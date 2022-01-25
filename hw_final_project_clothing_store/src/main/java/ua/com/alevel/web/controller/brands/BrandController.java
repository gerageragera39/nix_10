package ua.com.alevel.web.controller.brands;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ua.com.alevel.facade.brands.BrandFacade;
import ua.com.alevel.facade.clothes.ClothesFacade;
import ua.com.alevel.web.controller.AbstractController;
import ua.com.alevel.web.dto.response.brands.BrandResponseDto;

@Controller
public class BrandController extends AbstractController {

    private final BrandFacade brandFacade;
    private final ClothesFacade clothesFacade;

    public BrandController(BrandFacade brandFacade, ClothesFacade clothesFacade) {
        this.brandFacade = brandFacade;
        this.clothesFacade = clothesFacade;
    }

    @GetMapping("/personal/brands/details/{id}")
    public String personalBrandDetails(@PathVariable Long id, Model model) {
        BrandResponseDto dto = brandFacade.findById(id);
        model.addAttribute("brand", dto);
        return "pages/personals/brands/brands_details";
    }
}
