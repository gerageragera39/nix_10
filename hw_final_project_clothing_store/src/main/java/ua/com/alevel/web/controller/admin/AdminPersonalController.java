package ua.com.alevel.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.facade.users.PersonalFacade;
import ua.com.alevel.web.controller.AbstractController;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.users.PersonalResponseDto;

@Controller
@RequestMapping("/admin/personals")
public class AdminPersonalController extends AbstractController {

    private final HeaderName[] columnNames = new HeaderName[]{
            new HeaderName("#", null, null),
            new HeaderName("E-mail", "email", "email"),
            new HeaderName("Role Type", "role_type", "roleType"),
            new HeaderName("Enabled", "enabled", "enabled"),
            new HeaderName("Registered", "created", "created"),
            new HeaderName("details", null, null)
    };

    private final PersonalFacade personalFacade;

    public AdminPersonalController(PersonalFacade personalFacade) {
        this.personalFacade = personalFacade;
    }

    @GetMapping
    public String findAll(Model model, WebRequest request) {
        PageData<PersonalResponseDto> response = personalFacade.findAll(request);
        initDataTable(response, columnNames, model);
        model.addAttribute("createUrl", "/admin/personals/all");
        model.addAttribute("createNew", "/admin/personals/new");
        model.addAttribute("cardHeader", "All Personals");
        return "pages/admin/personals/personals_all";
    }

    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        return findAllRedirect(request, model, "admin/personals");
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        personalFacade.delete(id);
        return "redirect:/admin/clothes";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Long id, Model model) {
        PersonalResponseDto dto = personalFacade.findById(id);
        model.addAttribute("person", dto);
        return "pages/admin/personals/personals_details";
    }

    @GetMapping("/change/{id}")
    public String changeEnable(@PathVariable Long id, Model model) {
        personalFacade.changeEnable(id);
        PersonalResponseDto dto = personalFacade.findById(id);
        model.addAttribute("person", dto);
        return "redirect:/admin/personals/details/" + id;
    }
}
