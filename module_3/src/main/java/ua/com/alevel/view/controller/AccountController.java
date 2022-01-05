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
import ua.com.alevel.view.dto.response.AccountResponseDto;
import ua.com.alevel.view.dto.response.PageData;

import java.util.Map;

@Controller
@RequestMapping("/accounts")
public class AccountController extends BaseController {

    private String tempUserEmail;

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
    private Long tempId;
    private int flag;

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

    @GetMapping("/allByUser/{userId}")
    public String findAllByUserId(@PathVariable Long userId, Model model, WebRequest request) {
        PageData<AccountResponseDto> response = accountFacade.findAll(userId, request);
        initDataTable(response, columnNames, model);
        setTempId(userId);
        setFlag(1);
        model.addAttribute("pageData", response);
        model.addAttribute("createUrl", "/accounts/all");
        model.addAttribute("createNew", "/accounts/new");
        model.addAttribute("cardHeader", "User Accounts");
        return "pages/accounts/accounts_all";
    }

    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (MapUtils.isNotEmpty(parameterMap)) {
            parameterMap.forEach(model::addAttribute);
        }
        return switch (flag) {
            case 1 -> new ModelAndView("redirect:/accounts/allByUser/" + getTempId(), model);
            default -> new ModelAndView("redirect:/accounts", model);
        };
    }

    @GetMapping("/new")
    public String redirectToNewUserPage(Model model) {
        model.addAttribute("account", new AccountRequestDto());
        model.addAttribute("cardTypes", CardType.values());
        return "pages/accounts/accounts_new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("account") AccountRequestDto dto) {
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
        transactionController.setTempId(accountFacade.findById(id).getCardNumber());
        return "redirect:/transactions/new";
    }

    @GetMapping("/toUpdate/{id}")
    public String redirectToUpdatePersonPage(@PathVariable Long id, Model model) {
        AccountResponseDto accountResponseDto = accountFacade.findById(id);
        setTempId(id);
        model.addAttribute("account", new AccountRequestDto());
        model.addAttribute("cardTypes", CardType.values());
        return "pages/accounts/accounts_update";
    }

    @PostMapping("/update")
    public String updatePerson(@ModelAttribute("account") AccountRequestDto dto) {
        accountFacade.update(dto, getTempId());
        return "redirect:/accounts";
    }

    @GetMapping("/download/{id}")
    public String redirectToDownloadAccount(@PathVariable Long id, Model model) {
        accountFacade.writeOut(id);
        return "redirect:/download/file/write_out.csv";
    }

    public String getTempUserEmail() {
        return tempUserEmail;
    }

    public void setTempUserEmail(String tempUserEmail) {
        this.tempUserEmail = tempUserEmail;
    }

    public Long getTempId() {
        return tempId;
    }

    public void setTempId(Long tempId) {
        this.tempId = tempId;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
