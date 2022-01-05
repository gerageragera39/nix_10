package ua.com.alevel.view.controller;

import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.facade.AccountFacade;
import ua.com.alevel.facade.UserFacade;
import ua.com.alevel.persistence.cities.Cities;
import ua.com.alevel.view.dto.request.UserRequestDto;
import ua.com.alevel.view.dto.response.PageData;
import ua.com.alevel.view.dto.response.UserResponseDto;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

    private final AccountController accountController;

    private final HeaderName[] columnNames = new HeaderName[]{
            new HeaderName("#", null, null),
            new HeaderName("first name", "first_name", "firstName"),
            new HeaderName("last name", "last_name", "lastName"),
            new HeaderName("age", "age", "age"),
            new HeaderName("passport ID", null, null),
            new HeaderName("account count", "accountCount", "accountCount"),
            new HeaderName("details", null, null),
            new HeaderName("delete", null, null)
    };

    private final UserFacade userFacade;
    private Long tempId;

    public UserController(AccountController accountController, UserFacade userFacade, AccountFacade accountFacade) {
        this.accountController = accountController;
        this.userFacade = userFacade;
    }

    @GetMapping
    public String findAll(Model model, WebRequest request) {
        PageData<UserResponseDto> response = userFacade.findAll(request);
        initDataTable(response, columnNames, model);
        model.addAttribute("pageData", response);
        model.addAttribute("createUrl", "/users/all");
        model.addAttribute("createNew", "/users/new");
        model.addAttribute("cardHeader", "All Users");
        return "pages/users/users_all";
    }

    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (MapUtils.isNotEmpty(parameterMap)) {
            parameterMap.forEach(model::addAttribute);
        }
        return new ModelAndView("redirect:/users", model);
    }

    @GetMapping("/new")
    public String redirectToNewUserPage(Model model) {
        model.addAttribute("user", new UserRequestDto());
        model.addAttribute("cities", Cities.values());
        return "pages/users/users_new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("user") UserRequestDto dto) {
        boolean creatingFlag = userFacade.checkAll(dto);
        if (creatingFlag) {
            userFacade.create(dto, dto.getEmail());
            accountController.setTempUserEmail(dto.getEmail());
            return "redirect:/accounts/new";
        }
        return "error";
    }

    @GetMapping("/newAccount/{id}")
    public String redirectToNewAccountPage(@PathVariable Long id, Model model) {
        accountController.setTempUserEmail(userFacade.findById(id).getEmail());
        return "redirect:/accounts/new";
    }

    @GetMapping("/details/{id}")
    public String redirectToUserDetailsPage(@PathVariable Long id, Model model) {
        model.addAttribute("accounts", userFacade.findAccountsByUserId(id));
        model.addAttribute("user", userFacade.findById(id));
        return "pages/users/users_details";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        userFacade.delete(id);
        return "redirect:/users";
    }

    @GetMapping("/toRemove/{id}")
    public String redirectToRemovedAccountPage(@PathVariable Long id, Model model) {
        List<String> cardNames = userFacade.findAccountsByUserId(id).values().stream().toList();
        model.addAttribute("user", new UserRequestDto());
        model.addAttribute("cardNames", cardNames);
        return "pages/users/users_remove_account";
    }

    @PostMapping("/remove")
    public String removeCitizenship(@ModelAttribute("user") UserRequestDto dto) {
        return "redirect:/accounts/deleteByName/" + dto.getAccountName();
    }

    @GetMapping("/toUpdate/{id}")
    public String redirectToUpdatePersonPage(@PathVariable Long id, Model model) {
        setTempId(id);
        model.addAttribute("cities", Cities.values());
        model.addAttribute("user", new UserRequestDto());
        return "pages/users/users_update";
    }

    @PostMapping("/update")
    public String updatePerson(@ModelAttribute("user") UserRequestDto dto) {
        userFacade.update(dto, getTempId());
        return "redirect:/users";
    }

    @GetMapping("/download/{id}")
    public String redirectToDownloadUser(@PathVariable Long id, Model model) {
        userFacade.writeOut(id);
        return "redirect:/download/file/write_out.csv";
    }

    public Long getTempId() {
        return tempId;
    }

    public void setTempId(Long tempId) {
        this.tempId = tempId;
    }
}
