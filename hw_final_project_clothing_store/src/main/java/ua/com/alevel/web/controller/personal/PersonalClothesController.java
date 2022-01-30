package ua.com.alevel.web.controller.personal;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.com.alevel.facade.clothes.ClothesFacade;
import ua.com.alevel.facade.open.PLPFacade;
import ua.com.alevel.facade.products.ProductFacade;
import ua.com.alevel.facade.users.PersonalFacade;
import ua.com.alevel.persistence.entity.clothes.Image;
import ua.com.alevel.persistence.sex.Sexes;
import ua.com.alevel.util.SecurityUtil;
import ua.com.alevel.util.WebUtil;
import ua.com.alevel.web.controller.AbstractController;
import ua.com.alevel.web.dto.request.product.ProductRequestDto;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.clothes.ClothesResponseDto;
import ua.com.alevel.web.dto.response.open.ClothesPLPDto;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/personal/clothes")
public class PersonalClothesController extends AbstractController {

    private final ClothesFacade clothesFacade;
    private final ProductFacade productFacade;
    private final PersonalFacade personalFacade;
    private final PLPFacade plpFacade;

    private final HeaderName[] columnNames = new HeaderName[]{
            new HeaderName("Price", "price", "price")
    };

    public PersonalClothesController(ClothesFacade clothesFacade, ProductFacade productFacade, PersonalFacade personalFacade, PLPFacade plpFacade) {
        this.clothesFacade = clothesFacade;
        this.productFacade = productFacade;
        this.personalFacade = personalFacade;
        this.plpFacade = plpFacade;
    }

    @GetMapping
    private String allClothes(Model model, WebRequest webRequest) {
        PageData<ClothesPLPDto> response = plpFacade.findAll(webRequest);
        List<ClothesPLPDto> clothesList = response.getItems();
        initDataTable(response, columnNames, model);
        model.addAttribute("createUrl", "/personal/clothes/all");
        model.addAttribute("cardHeader", "All Clothes");
        model.addAttribute("pageData", response);
        model.addAttribute("clothes", clothesList);
        model.addAttribute("brands", plpFacade.findAllBrands());
        model.addAttribute("types", plpFacade.findAllTypes());
        model.addAttribute("sexes", Sexes.values());
        model.addAttribute("colors", plpFacade.findAllColors());
        model.addAttribute("sizes", plpFacade.findAllSizes());
        if (webRequest.getParameterMap().get("search_clothes") != null &&
                response.getItems().size() == 1) {
            return "redirect:/personal/clothes/product/" + response.getItems().get(0).getId();
        }

        if (response.getItems().size() == 0) {
            return "pages/personals/empty_personals";
        }
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
        model.addAttribute("firstImage", images.get(0));
        model.addAttribute("colors", clothesFacade.findColorsByThingId(id));
        model.addAttribute("sizes", clothesFacade.findSizesByThingId(id));
        return "pages/personals/clothes/clothes_details";
    }

    @GetMapping("/product/delete/{id}")
    private String deleteProduct(@PathVariable Long id) {
        productFacade.delete(id);
        return "redirect:/personal/clothes/bucket";
    }

    @PostMapping("/add/{id}")
    private String addToBucket(@PathVariable Long id, @ModelAttribute("product") ProductRequestDto dto) {
        ClothesResponseDto clothesResponseDto = clothesFacade.findById(id);
        dto.setWearId(id);
        dto.setPersonalEmail(SecurityUtil.getUsername());
        dto.setClg(clothesResponseDto.getClg());
        productFacade.create(dto);
        return "redirect:/personal/clothes/bucket";
    }

    @GetMapping("/product/plus/{id}")
    private String plus(@PathVariable Long id) {
        productFacade.redactQuantity(id, true);
        return "redirect:/personal/clothes/bucket";
    }

    @GetMapping("/product/minus/{id}")
    private String minus(@PathVariable Long id) {
        productFacade.redactQuantity(id, false);
        return "redirect:/personal/clothes/bucket";
    }

    @GetMapping("/suggestions")
    private @ResponseBody
    List<String> searchClothesNames(@RequestParam String query) {
        return clothesFacade.searchClothesNames(query);
    }

    @PostMapping("/search")
    private String searchClothes(@RequestParam String query, WebRequest webRequest, RedirectAttributes ra) {
        String referrer = webRequest.getHeader("referer");
        String[] url = referrer.split("\\?");
        if (url.length == 2) {
            if (url[1].charAt(0) == '&') {
                url[1] = String.copyValueOf(url[1].toCharArray(), 1, url[1].length() - 1);
            }
            String[] params = url[1].split("&");
            List<String[]> pairs = new ArrayList<>();
            for (String param : params) {
                String[] pair = param.split("=");
                pairs.add(pair);
            }
            for (String[] pair : pairs) {
                ra.addAttribute(pair[0], pair[1]);
            }
        }
        if (StringUtils.isNotBlank(query)) {
            ra.addAttribute(WebUtil.SEARCH_CLOTHES_PARAM, query);
        }

        return "redirect:/personal/clothes";
    }

    @GetMapping("/bucket")
    private String bucket(Model model) {
        model.addAttribute("products", productFacade.findByPersonalEmail(SecurityUtil.getUsername()));
        model.addAttribute("totalPrice", productFacade.findTotalPrice());
        return "pages/personals/basket";
    }
}
