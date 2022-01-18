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
import ua.com.alevel.facade.brands.BrandFacade;
import ua.com.alevel.web.controller.AbstractController;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.brands.BrandResponseDto;

@Controller
@RequestMapping("/admin/brands")
public class AdminBrandsController extends AbstractController {

    private final HeaderName[] columnNames = new HeaderName[] {
            new HeaderName("#", null, null),
            new HeaderName("logo", null, null),
            new HeaderName("name", "name", "name"),
            new HeaderName("created", "created", "created"),
            new HeaderName("details", null, null),
            new HeaderName("delete", null, null)
    };

    private final BrandFacade brandFacade;

    public AdminBrandsController(BrandFacade brandFacade) {
        this.brandFacade = brandFacade;
    }

    @GetMapping
    public String findAll(Model model, WebRequest request) {
        PageData<BrandResponseDto> response = brandFacade.findAll(request);
        initDataTable(response, columnNames, model);
        model.addAttribute("createUrl", "/admin/brands/all");
        model.addAttribute("createNew", "/admin/brands/new");
        model.addAttribute("cardHeader", "All Brands");
        return "pages/admin/brands/brands_all";
    }

    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        return findAllRedirect(request, model, "admin/brands");
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        brandFacade.delete(id);
        return "redirect:/admin/brands";
    }
}
