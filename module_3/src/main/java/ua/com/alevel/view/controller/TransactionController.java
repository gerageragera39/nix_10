package ua.com.alevel.view.controller;

import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.facade.CategoryFacade;
import ua.com.alevel.facade.TransactionFacade;
import ua.com.alevel.persistence.entity.Account;
import ua.com.alevel.persistence.entity.User;
import ua.com.alevel.view.dto.request.TransactionRequestDto;
import ua.com.alevel.view.dto.response.PageData;
import ua.com.alevel.view.dto.response.TransactionResponseDto;

import java.util.Map;

@Controller
@RequestMapping("/transactions")
public class TransactionController extends BaseController {

    private final HeaderName[] columnNames = new HeaderName[]{
            new HeaderName("#", null, null),
            new HeaderName("amount", "amount", "amount"),
            new HeaderName("category", null, null),
            new HeaderName("details", null, null)
    };

    private final TransactionFacade transactionFacade;
    private final CategoryFacade categoryFacade;
    private String tempId;
    private int flag;

    public TransactionController(TransactionFacade transactionFacade, CategoryFacade categoryFacade) {
        this.transactionFacade = transactionFacade;
        this.categoryFacade = categoryFacade;
    }

    @GetMapping
    public String findAll(Model model, WebRequest request) {
        PageData<TransactionResponseDto> response = transactionFacade.findAll(request);
        initDataTable(response, columnNames, model);
        model.addAttribute("pageData", response);
        model.addAttribute("createUrl", "/transactions/all");
        model.addAttribute("createNew", "/transactions/new");
        model.addAttribute("cardHeader", "All Transactions");
        return "pages/transactions/transactions_all";
    }

    @GetMapping("/allByAccount/{accountId}")
    public String findAllByAccountId(@PathVariable Long accountId, Model model, WebRequest request) {
        PageData<TransactionResponseDto> response = transactionFacade.findAll(Account.class, accountId, request);
        initDataTable(response, columnNames, model);
        setTempId(accountId.toString());
        setFlag(1);
        model.addAttribute("pageData", response);
        model.addAttribute("createUrl", "/transactions/all");
        model.addAttribute("createNew", "/transactions/new");
        model.addAttribute("cardHeader", "Account Transactions");
        return "pages/transactions/transactions_all";
    }

    @GetMapping("/allByUser/{userId}")
    public String findAllByUserId(@PathVariable Long userId, Model model, WebRequest request) {
        PageData<TransactionResponseDto> response = transactionFacade.findAll(User.class, userId, request);
        initDataTable(response, columnNames, model);
        setTempId(userId.toString());
        setFlag(2);
        model.addAttribute("pageData", response);
        model.addAttribute("createUrl", "/transactions/all");
        model.addAttribute("createNew", "/transactions/new");
        model.addAttribute("cardHeader", "User Transactions");
        return "pages/transactions/transactions_all";
    }

    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (MapUtils.isNotEmpty(parameterMap)) {
            parameterMap.forEach(model::addAttribute);
        }
        return switch (flag) {
            case 1 -> new ModelAndView("redirect:/transactions/allByAccount/" + getTempId(), model);
            case 2 -> new ModelAndView("redirect:/transactions/allByUser/" + getTempId(), model);
            default -> new ModelAndView("redirect:/transactions", model);
        };
    }

    @GetMapping("/details/{id}")
    public String redirectToUserDetailsPage(@PathVariable Long id, Model model) {
        model.addAttribute("transaction", transactionFacade.findById(id));
        model.addAttribute("account", transactionFacade.findByAccountByTransactionId(id));
        model.addAttribute("user", transactionFacade.findUserByTransactionId(id));
        return "pages/transactions/transactions_details";
    }

    @GetMapping("/new")
    public String redirectToNewUserPage(Model model) {
        model.addAttribute("transaction", new TransactionRequestDto());
        model.addAttribute("categories", categoryFacade.getAllCategoryNames());
        return "pages/transactions/transactions_new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("transaction") TransactionRequestDto dto) {
        transactionFacade.create(dto, getTempId());
        return "redirect:/accounts";
    }

    public String getTempId() {
        return tempId;
    }

    public void setTempId(String tempId) {
        this.tempId = tempId;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
