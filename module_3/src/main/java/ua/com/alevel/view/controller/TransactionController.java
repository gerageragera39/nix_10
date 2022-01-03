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
import ua.com.alevel.persistence.cardType.CardType;
import ua.com.alevel.persistence.entity.Account;
import ua.com.alevel.persistence.entity.User;
import ua.com.alevel.view.dto.request.AccountRequestDto;
import ua.com.alevel.view.dto.request.TransactionRequestDto;
import ua.com.alevel.view.dto.response.PageData;
import ua.com.alevel.view.dto.response.TransactionResponseDto;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/transactions")
public class TransactionController extends BaseController {

    private String tempAccountId;

    private final HeaderName[] columnNames = new HeaderName[]{
            new HeaderName("#", null, null),
            new HeaderName("amount", "amount", "amount"),
            new HeaderName("category", null, null),
            new HeaderName("details", null, null)
    };

    private final TransactionFacade transactionFacade;
    private final CategoryFacade categoryFacade;

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
        model.addAttribute("pageData", response);
        model.addAttribute("createUrl", "/transactions/all");
        model.addAttribute("createNew", "/transactions/new");
        model.addAttribute("cardHeader", "All Transactions");
        return "pages/transactions/transactions_all";
    }

    @GetMapping("/allByUser/{userId}")
    public String findAllByUserId(@PathVariable Long userId, Model model, WebRequest request) {
        PageData<TransactionResponseDto> response = transactionFacade.findAll(User.class, userId, request);
        initDataTable(response, columnNames, model);
        model.addAttribute("pageData", response);
        model.addAttribute("createUrl", "/transactions/all");
        model.addAttribute("createNew", "/transactions/new");
        model.addAttribute("cardHeader", "All Transactions");
        return "pages/transactions/transactions_all";
    }

    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (MapUtils.isNotEmpty(parameterMap)) {
            parameterMap.forEach(model::addAttribute);
        }
        return new ModelAndView("redirect:/transactions", model);
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
        transactionFacade.create(dto, getTempAccountId());
        return "redirect:/accounts";
//        return "redirect:/accounts/details/{" + getTempAccountId() + "}";
    }

    public String getTempAccountId() {
        return tempAccountId;
    }

    public void setTempAccountId(String tempAccountId) {
        this.tempAccountId = tempAccountId;
    }
}
