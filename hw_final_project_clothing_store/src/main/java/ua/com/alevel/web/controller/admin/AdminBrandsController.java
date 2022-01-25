package ua.com.alevel.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.facade.brands.BrandFacade;
import ua.com.alevel.web.controller.AbstractController;
import ua.com.alevel.web.dto.request.brands.BrandsRequestDto;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.brands.BrandResponseDto;

@Controller
@RequestMapping("/admin/brands")
public class AdminBrandsController extends AbstractController {

    private final HeaderName[] columnNames = new HeaderName[]{
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

    @GetMapping("/new")
    public String redirectToNewBookPage(Model model) {
        model.addAttribute("brand", new BrandsRequestDto());
        return "pages/admin/brands/brands_new";
    }

    @PostMapping("/create")
    public String createNewDepartment(@ModelAttribute("brand") BrandsRequestDto dto) {
        brandFacade.create(dto);
        return "redirect:/admin/brands";
    }

    @GetMapping("/details/{id}")
    public String adminBrandDetails(@PathVariable Long id, Model model) {
        BrandResponseDto dto = brandFacade.findById(id);
        model.addAttribute("brand", dto);
        return "pages/admin/brands/brands_details";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        brandFacade.delete(id);
        return "redirect:/admin/brands";
    }

    @GetMapping("/to/update/{id}")
    public String toUpdate(@PathVariable Long id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("brand", new BrandsRequestDto());
        return "pages/admin/brands/brands_update";
    }

    @PostMapping("/update/{id}")
    public String updateThing(@ModelAttribute("brand") BrandsRequestDto dto, @PathVariable Long id) {
        brandFacade.update(dto, id);
        return "redirect:/admin/brands/details/" + id;
    }
}
