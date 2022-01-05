package ua.com.alevel.view.controller;

import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.facade.CategoryFacade;
import ua.com.alevel.view.dto.request.CategoryRequestDto;
import ua.com.alevel.view.dto.response.CategoryResponseDto;
import ua.com.alevel.view.dto.response.PageData;

import java.util.Map;

@Controller
@RequestMapping("/categories")
public class CategoryController extends BaseController {

    private final HeaderName[] columnNames = new HeaderName[]{
            new HeaderName("#", null, null),
            new HeaderName("name", "name", "name"),
            new HeaderName("finances", "finances", "finances"),
            new HeaderName("categoryCount", "categoryCount", "categoryCount")
    };

    private final CategoryFacade categoryFacade;

    public CategoryController(CategoryFacade categoryFacade) {
        this.categoryFacade = categoryFacade;
    }

    @GetMapping
    public String findAll(Model model, WebRequest request) {
        PageData<CategoryResponseDto> response = categoryFacade.findAll(request);
        initDataTable(response, columnNames, model);
        model.addAttribute("pageData", response);
        model.addAttribute("createUrl", "/categories/all");
        model.addAttribute("createNew", "/categories/new");
        model.addAttribute("cardHeader", "All Categories");
        return "pages/categories/categories_all";
    }

    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (MapUtils.isNotEmpty(parameterMap)) {
            parameterMap.forEach(model::addAttribute);
        }
        return new ModelAndView("redirect:/categories", model);
    }

    @GetMapping("/new")
    public String redirectToNewUserPage(Model model) {
        model.addAttribute("category", new CategoryRequestDto());
        boolean[] booleans = {true, false};
        model.addAttribute("booleans", booleans);
        return "pages/categories/categories_new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("category") CategoryRequestDto dto) {
        categoryFacade.create(dto, dto.getName());
        return "redirect:/categories";
    }
}
