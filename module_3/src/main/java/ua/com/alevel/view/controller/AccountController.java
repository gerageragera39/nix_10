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
import ua.com.alevel.persistence.cardType.CardType;
import ua.com.alevel.view.dto.request.AccountRequestDto;
import ua.com.alevel.view.dto.request.UserRequestDto;
import ua.com.alevel.view.dto.response.AccountResponseDto;
import ua.com.alevel.view.dto.response.PageData;
import ua.com.alevel.view.dto.response.UserResponseDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/accounts")
public class AccountController extends BaseController {

    private String tempUserEmail;
    private String tempAccountName;

    private final HeaderName[] columnNames = new HeaderName[]{
            new HeaderName("#", null, null),
            new HeaderName("card number", "card_number", "cardNumber"),
            new HeaderName("balance", "balance", "balance"),
            new HeaderName("cardType", "cardType", "cardType"),
            new HeaderName("transaction count", "transactionCount", "transactionCount"),
            new HeaderName("details", null, null),
            new HeaderName("delete", null, null)
    };

    private final TransactionController transactionController;
    private final AccountFacade accountFacade;

    public AccountController(TransactionController transactionController, AccountFacade accountFacade, UserFacade userFacade) {
        this.transactionController = transactionController;
        this.accountFacade = accountFacade;
    }

    @GetMapping
    public String findAll(Model model, WebRequest request) {
        PageData<AccountResponseDto> response = accountFacade.findAll(request);
        initDataTable(response, columnNames, model);
        model.addAttribute("pageData", response);
        model.addAttribute("createUrl", "/accounts/all");
        model.addAttribute("createNew", "/accounts/new");
        model.addAttribute("cardHeader", "All Accounts");
        return "pages/accounts/accounts_all";
    }

    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (MapUtils.isNotEmpty(parameterMap)) {
            parameterMap.forEach(model::addAttribute);
        }
        return new ModelAndView("redirect:/accounts", model);
    }

    @GetMapping("/new")
    public String redirectToNewUserPage(Model model) {
        model.addAttribute("account", new AccountRequestDto());
        model.addAttribute("cardTypes", CardType.values());
        return "pages/accounts/accounts_new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("account") AccountRequestDto dto) {
//        dto.setCardNumber(UUID.randomUUID().toString());
        accountFacade.create(dto, getTempUserEmail());
        return "redirect:/users";
    }

    @GetMapping("/details/{id}")
    public String redirectToUserDetailsPage(@PathVariable Long id, Model model) {
        model.addAttribute("transactions", accountFacade.findTransactionsByAccountId(id));
        model.addAttribute("account", accountFacade.findById(id));
        model.addAttribute("user", accountFacade.findUserByAccountId(id));
        return "pages/accounts/accounts_details";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        accountFacade.delete(id);
        return "redirect:/accounts";
    }

    @GetMapping("/deleteByName/{name}")
    public String deleteByName(@PathVariable String name) {
        accountFacade.delete(name);
        return "redirect:/accounts";
    }

    @GetMapping("/newTransaction/{id}")
    public String redirectToNewAccountPage(@PathVariable Long id, Model model) {
        transactionController.setTempAccountId(accountFacade.findById(id).getCardNumber());
        return "redirect:/transactions/new";
    }


    public String getTempUserEmail() {
        return tempUserEmail;
    }

    public void setTempUserEmail(String tempUserEmail) {
        this.tempUserEmail = tempUserEmail;
    }

    public String getTempAccountName() {
        return tempAccountName;
    }

    public void setTempAccountName(String tempAccountName) {
        this.tempAccountName = tempAccountName;
    }
}
