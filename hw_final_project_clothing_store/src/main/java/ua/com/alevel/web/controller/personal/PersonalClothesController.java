package ua.com.alevel.web.controller.personal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.facade.clothes.ClothesFacade;
import ua.com.alevel.facade.open.PLPFacade;
import ua.com.alevel.facade.products.ProductFacade;
import ua.com.alevel.facade.users.PersonalFacade;
import ua.com.alevel.persistence.entity.clothes.Image;
import ua.com.alevel.util.SecurityUtil;
import ua.com.alevel.web.controller.AbstractController;
import ua.com.alevel.web.dto.request.product.ProductRequestDto;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.clothes.ClothesResponseDto;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/personal/clothes")
public class PersonalClothesController extends AbstractController {

    private final ClothesFacade clothesFacade;
    private final ProductFacade productFacade;
    private final PersonalFacade personalFacade;
    private final PLPFacade plpFacade;

    public PersonalClothesController(ClothesFacade clothesFacade, ProductFacade productFacade, PersonalFacade personalFacade, PLPFacade plpFacade) {
        this.clothesFacade = clothesFacade;
        this.productFacade = productFacade;
        this.personalFacade = personalFacade;
        this.plpFacade = plpFacade;
    }

    @GetMapping
    private String allClothes(Model model, WebRequest webRequest) {
//        PageData<ClothesResponseDto> response = clothesFacade.personalFindAll(webRequest);
        PageData<ClothesResponseDto> response = plpFacade.findAll(webRequest);
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
        model.addAttribute("id", id);
        model.addAttribute("thing", dto);
        model.addAttribute("images", imageList);
        model.addAttribute("product", new ProductRequestDto());
//        model.addAttribute("product", new ClothesRequestDto());
        model.addAttribute("firstImage", images.get(0));
        model.addAttribute("colors", clothesFacade.findColorsByThingId(id));
        model.addAttribute("sizes", clothesFacade.findSizesByThingId(id));
        return "pages/personals/clothes/clothes_details";
//        return "pages/personals/clothes/product_details";
    }

    @PostMapping("/add/{id}")
    private String addToBucket(@PathVariable Long id, @ModelAttribute("product") ProductRequestDto dto) {
//        , @ModelAttribute("product") ProductRequestDto dto
//        ProductRequestDto dto = new ProductRequestDto();
        ClothesResponseDto clothesResponseDto = clothesFacade.findById(id);
        dto.setWearId(id);
        dto.setPersonalEmail(SecurityUtil.getUsername());
        dto.setClg(clothesResponseDto.getClg());
        productFacade.create(dto);
        return "redirect:/personal/clothes/product/" + id;
    }

    @GetMapping("/suggestions")
    private @ResponseBody List<String> searchClothesNames(@RequestParam String query) {
        return clothesFacade.searchClothesNames(query);
    }

    @GetMapping("/bucket")
    private String bucket(Model model) {
//        model.addAttribute("products", personalFacade.findByEmail(SecurityUtil.getUsername()).getProducts());
        model.addAttribute("products", productFacade.findByPersonalEmail(SecurityUtil.getUsername()));
//        return "pages/personals/shopping_cart";
        return "pages/personals/bucket";
    }
}
