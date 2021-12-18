package ua.com.alevel.view.controller;

import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.facade.CountriesFacade;
import ua.com.alevel.facade.PopulationFacade;
import ua.com.alevel.persistence.sex.Sex;
import ua.com.alevel.view.dto.request.PopulationRequestDto;
import ua.com.alevel.view.dto.response.PageData;
import ua.com.alevel.view.dto.response.PopulationResponseDto;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/population")
public class PopulationController extends BaseController {

    private final PopulationFacade populationFacade;
    private final CountriesFacade countriesFacade;

    private final HeaderName[] columnNames = new HeaderName[]{
            new HeaderName("#", null, null),
            new HeaderName("first name", "first_ame", "firstName"),
            new HeaderName("last name", "last_name", "lastName"),
            new HeaderName("age", "age", "age"),
            new HeaderName("sex", "sex", "sex"),
            new HeaderName("passport ID", null, null),
            new HeaderName("country count", "countryCount", "countryCount"),
            new HeaderName("details", null, null),
            new HeaderName("delete", null, null)
    };

    private String tempPassportId;
    private long tempPersonId;

    public PopulationController(PopulationFacade populationFacade, CountriesFacade countriesFacade) {
        this.populationFacade = populationFacade;
        this.countriesFacade = countriesFacade;
    }

    @GetMapping
    public String findAll(Model model, WebRequest request) {
        PageData<PopulationResponseDto> response = populationFacade.findAll(request, true);
        initDataTable(response, columnNames, model);
        model.addAttribute("pageData", response);
        model.addAttribute("createUrl", "/population/all");
        model.addAttribute("createNew", "/population/new");
        model.addAttribute("cardHeader", "All People With Citizenship");
        return "pages/population/population_all";
    }

    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (MapUtils.isNotEmpty(parameterMap)) {
            parameterMap.forEach(model::addAttribute);
        }
        return new ModelAndView("redirect:/population", model);
    }

    @GetMapping("/new")
    public String redirectToNewPopulationPage(Model model) {
        model.addAttribute("person", new PopulationRequestDto());
        model.addAttribute("sexes", Sex.values());
        List<String> names = countriesFacade.findAllCountriesNames();
        model.addAttribute("countryNames", names);
        return "pages/population/population_new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("person") PopulationRequestDto dto) {
        System.out.println("dto = " + dto);
        populationFacade.create(dto);
        populationFacade.addRelation(dto);
        return "redirect:/population";
    }

    @GetMapping("/сitizenship/{id}")
    public String redirectToNewCitizenshipPage(@PathVariable Long id, Model model) {
        List<String> names = populationFacade.notAddedCountryNamesByPersonId(id);
        PopulationResponseDto populationResponseDto = populationFacade.findById(id);
        setTempPassportId(populationResponseDto.getPassportID());
        model.addAttribute("person", new PopulationRequestDto());
        model.addAttribute("countryNames", names);
        return "pages/population/population_new_citizenship";
    }

    @PostMapping("/add")
    public String createNewCitizenship(@ModelAttribute("person") PopulationRequestDto dto) {
        dto.setPassportID(getTempPassportId());
        System.out.println("dto = " + dto);
        populationFacade.addRelation(dto);
        return "redirect:/population";
    }

    @GetMapping("/toRemove/{id}")
    public String redirectToRemovedCitizenshipPage(@PathVariable Long id, Model model) {
        PopulationResponseDto populationResponseDto = populationFacade.findById(id);
        setTempPassportId(populationResponseDto.getPassportID());
        List<String> names = populationFacade.findNamesByPersonId(id);

        model.addAttribute("person", new PopulationRequestDto());
        model.addAttribute("countryNames", names);
        return "pages/population/population_remove_citizenship";
    }

    @PostMapping("/remove")
    public String removeCitizenship(@ModelAttribute("person") PopulationRequestDto dto) {
        dto.setPassportID(getTempPassportId());
        System.out.println("dto = " + dto);
        populationFacade.removeRelation(dto);
        return "redirect:/population";
    }

    @GetMapping("/toUpdate/{id}")
    public String redirectToUpdatePersonPage(@PathVariable Long id, Model model) {
        PopulationResponseDto populationResponseDto = populationFacade.findById(id);
        setTempPassportId(populationResponseDto.getPassportID());
        setTempPersonId(id);
        model.addAttribute("sexes", Sex.values());
        model.addAttribute("person", new PopulationRequestDto());
        return "pages/population/population_update";
    }

    @PostMapping("/update")
    public String updatePerson(@ModelAttribute("person") PopulationRequestDto dto) {
        dto.setPassportID(getTempPassportId());
        System.out.println("dto = " + dto);
        populationFacade.update(dto, getTempPersonId());
        return "redirect:/population";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        populationFacade.delete(id);
        return "redirect:/population";
    }

    @GetMapping("/details/{id}")
    public String redirectToNewAuthorPage(@PathVariable Long id, Model model) {
        model.addAttribute("countries", populationFacade.findCountriesByPersonId(id));
        model.addAttribute("person", populationFacade.findById(id));
        return "pages/population/population_details";
    }

    @GetMapping("/notVisible")
    public String findNotVisible(Model model, WebRequest request) {
        PageData<PopulationResponseDto> response = populationFacade.findAll(request, false);
        initDataTable(response, columnNames, model);
        model.addAttribute("pageData", response);
        model.addAttribute("createUrl", "/population/allNotVisible");
        model.addAttribute("createNew", "/population/new");
        model.addAttribute("cardHeader", "All People Without Citizenship");
        return "pages/population/population_all";
    }

    @PostMapping("/allNotVisible")
    public ModelAndView findAllNotVisibleRedirect(WebRequest request, ModelMap model) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (MapUtils.isNotEmpty(parameterMap)) {
            parameterMap.forEach(model::addAttribute);
        }
        return new ModelAndView("redirect:/population/notVisible", model);
    }

    public String getTempPassportId() {
        return tempPassportId;
    }

    public void setTempPassportId(String tempPassportId) {
        this.tempPassportId = tempPassportId;
    }

    public long getTempPersonId() {
        return tempPersonId;
    }

    public void setTempPersonId(long tempPersonId) {
        this.tempPersonId = tempPersonId;
    }
}
