package ua.com.alevel.web.controller.personal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.alevel.facade.users.PersonalFacade;
import ua.com.alevel.util.SecurityUtil;
import ua.com.alevel.web.dto.request.users.PersonalRequestDto;

@Controller
@RequestMapping("/personal")
public class PersonalController {

    private final PersonalFacade personalFacade;

    public PersonalController(PersonalFacade personalFacade) {
        this.personalFacade = personalFacade;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("personal", personalFacade.findByEmail(SecurityUtil.getUsername()));
        return "pages/personals/dashboard";
    }

    @GetMapping("/dashboard/to/edit")
    public String toEdit(Model model) {
        model.addAttribute("personal", new PersonalRequestDto());
        return "pages/personals/personals_edit";
    }

    @PostMapping("/dashboard/edit")
    public String edit(@ModelAttribute("personal") PersonalRequestDto dto) {
        personalFacade.update(dto, personalFacade.findByEmail(SecurityUtil.getUsername()).getId());
        return "redirect:/personal/dashboard";
    }

    @GetMapping("/main")
    private String main() {
        return "pages/personals/personals_main_page";
    }

    @GetMapping("/about")
    private String about() {
        return "pages/personals/personals_about";
    }

    @GetMapping("/contacts")
    private String contacts() {
        return "pages/personals/personals_contacts";
    }
}
