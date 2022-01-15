package ua.com.alevel.web.controller.personal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.facade.clothes.ClothesFacade;
import ua.com.alevel.web.controller.AbstractController;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.clothes.ClothesResponseDto;

import java.util.List;

@Controller
@RequestMapping("/personal/clothes")
public class PersonalClothesController extends AbstractController {

    private final ClothesFacade clothesFacade;

    public PersonalClothesController(ClothesFacade clothesFacade) {
        this.clothesFacade = clothesFacade;
    }

    @GetMapping
    private String allClothes(Model model, WebRequest webRequest) {
        PageData<ClothesResponseDto> response = clothesFacade.findAll(webRequest);
        List<ClothesResponseDto> clothesList = response.getItems();
        model.addAttribute("createUrl", "/personal/clothes/all");
        model.addAttribute("cardHeader", "All Clothes");
        model.addAttribute("pageData", response);
        model.addAttribute("clothes", clothesList);
        return "pages/personals/clothes/clothes_all";
    }

    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        return findAllRedirect(request, model, "personal/clothes");
    }
}
