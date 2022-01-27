package ua.com.alevel.web.controller.open;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.alevel.facade.clothes.ClothesFacade;
import ua.com.alevel.facade.open.PLPFacade;
import ua.com.alevel.persistence.entity.clothes.Image;
import ua.com.alevel.web.controller.AbstractController;
import ua.com.alevel.web.dto.response.clothes.ClothesResponseDto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@Validated
@Controller
@RequestMapping("/clothes/product")
public class PDPController extends AbstractController {

    private final PLPFacade plpFacade;
    private final ClothesFacade clothesFacade;

    public PDPController(PLPFacade plpFacade, ClothesFacade clothesFacade) {
        this.plpFacade = plpFacade;
        this.clothesFacade = clothesFacade;
    }

    @GetMapping("/{id}")
    public String details(@PathVariable @Min(value = 1, message = "incorrect id") Long id, Model model) {
        ClothesResponseDto dto = plpFacade.findById(id);
        List<Image> images = dto.getImages();
        List<Image> imageList = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            if (i != 0) {
                imageList.add(images.get(i));
            }
        }
        model.addAttribute("thing", dto);
        model.addAttribute("id", id);
        model.addAttribute("images", imageList);
        model.addAttribute("firstImage", images.get(0));
        model.addAttribute("colors", clothesFacade.findColorsByThingId(id));
        model.addAttribute("sizes", clothesFacade.findSizesByThingId(id));
        return "pages/open/pdp";
    }
}
