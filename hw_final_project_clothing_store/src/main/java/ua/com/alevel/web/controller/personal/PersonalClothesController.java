package ua.com.alevel.web.controller.personal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.facade.clothes.ClothesFacade;
import ua.com.alevel.persistence.entity.clothes.Image;
import ua.com.alevel.web.controller.AbstractController;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.clothes.ClothesResponseDto;

import java.util.ArrayList;
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
        PageData<ClothesResponseDto> response = clothesFacade.personalFindAll(webRequest);
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

//    @GetMapping("/details/{id}")
//    public String details(@PathVariable Long id, Model model) {
//        ClothesResponseDto dto = clothesFacade.findById(id);
//        List<Image> images = dto.getImages();
//        images.remove(0);
//        model.addAttribute("thing", dto);
//        model.addAttribute("images", images);
//        model.addAttribute("firstImage", images.get(0));
//        model.addAttribute("colors", clothesFacade.findColorsByThingId(id));
//        model.addAttribute("sizes", clothesFacade.findSizesByThingId(id));
//        return "clothes_details_test";
//    }

    @GetMapping("/product/{id}")
    public String product(@PathVariable Long id, Model model) {
        ClothesResponseDto dto = clothesFacade.findById(id);
        List<Image> images = dto.getImages();
        List<Image> imageList = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            if (i != 0) {
                imageList.add(images.get(i));
            }
        }
        model.addAttribute("thing", dto);
        model.addAttribute("images", imageList);
        model.addAttribute("firstImage", images.get(0));
        model.addAttribute("colors", clothesFacade.findColorsByThingId(id));
        model.addAttribute("sizes", clothesFacade.findSizesByThingId(id));
        return "pages/personals/clothes/clothes_details";
    }
}
