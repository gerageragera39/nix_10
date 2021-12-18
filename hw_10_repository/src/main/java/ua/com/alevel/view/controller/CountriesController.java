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
import ua.com.alevel.view.dto.request.CountriesRequestDto;
import ua.com.alevel.view.dto.response.CountriesResponseDto;
import ua.com.alevel.view.dto.response.PageData;

import java.util.Map;

@Controller
@RequestMapping("/countries")
public class CountriesController extends BaseController {

    private final CountriesFacade countriesFacade;
    private final PopulationFacade populationFacade;

    private final HeaderName[] columnNames = new HeaderName[]{
            new HeaderName("#", null, null),
            new HeaderName("country name", "country_name", "nameOfCountry"),
            new HeaderName("ISO", "ISO", "ISO"),
            new HeaderName("people count", "peopleCount", "personCount"),
            new HeaderName("details", null, null),
            new HeaderName("delete", null, null)
    };

    private long tempCountryId;

    public CountriesController(CountriesFacade countriesFacade, PopulationFacade populationFacade) {
        this.countriesFacade = countriesFacade;
        this.populationFacade = populationFacade;
    }

    @GetMapping
    public String findAll(Model model, WebRequest request) {
        PageData<CountriesResponseDto> response = countriesFacade.findAll(request);
        initDataTable(response, columnNames, model);
        model.addAttribute("pageData", response);
        model.addAttribute("createUrl", "/countries/all");
        model.addAttribute("createNew", "/countries/new");
        model.addAttribute("cardHeader", "All Countries");
        return "pages/countries/countries_all";
    }

    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (MapUtils.isNotEmpty(parameterMap)) {
            parameterMap.forEach(model::addAttribute);
        }
        return new ModelAndView("redirect:/countries", model);
    }

    @GetMapping("/new")
    public String redirectToNewPopulationPage(Model model) {
        model.addAttribute("country", new CountriesRequestDto());
        return "pages/countries/countries_new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("country") CountriesRequestDto dto) {
        countriesFacade.create(dto);
        return "redirect:/countries";
    }

    @GetMapping("/toUpdate/{id}")
    public String redirectToUpdateCountryPage(@PathVariable Long id, Model model) {
        CountriesResponseDto countriesResponseDto = countriesFacade.findById(id);
        setTempCountryId(id);
        model.addAttribute("country", new CountriesRequestDto());
        return "pages/countries/countries_update";
    }

    @PostMapping("/update")
    public String updateCountry(@ModelAttribute("country") CountriesRequestDto dto) {
        System.out.println("dto = " + dto);
        countriesFacade.update(dto, getTempCountryId());
        return "redirect:/countries";
    }

    @GetMapping("/details/{id}")
    public String redirectToNewAuthorPage(@PathVariable Long id, Model model) {
        model.addAttribute("country", countriesFacade.findById(id));
        model.addAttribute("people", countriesFacade.findPeopleByCountryId(id));
        return "pages/countries/countries_details";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        countriesFacade.delete(id);
        return "redirect:/countries";
    }

    public long getTempCountryId() {
        return tempCountryId;
    }

    public void setTempCountryId(long tempCountryId) {
        this.tempCountryId = tempCountryId;
    }
}
