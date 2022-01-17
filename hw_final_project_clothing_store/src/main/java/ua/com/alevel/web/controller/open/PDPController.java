package ua.com.alevel.web.controller.open;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.alevel.facade.open.PLPFacade;
import ua.com.alevel.web.controller.AbstractController;
import ua.com.alevel.web.dto.response.clothes.ClothesResponseDto;

@Validated
@Controller
@RequestMapping("/clothes/details")
public class PDPController extends AbstractController {

    private final PLPFacade plpFacade;

    public PDPController(PLPFacade plpFacade) {
        this.plpFacade = plpFacade;
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        ClothesResponseDto dto = plpFacade.findById(id);
        model.addAttribute("thing", dto);
        return "pages/open/clothes/clothes_details";
    }
}
