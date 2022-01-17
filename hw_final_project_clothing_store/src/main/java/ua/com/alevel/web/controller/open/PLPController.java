package ua.com.alevel.web.controller.open;

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
import ua.com.alevel.facade.open.PLPFacade;
import ua.com.alevel.web.controller.AbstractController;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.clothes.ClothesResponseDto;
import ua.com.alevel.web.dto.response.open.ClothesPLPDto;

import java.util.List;

@Controller
@RequestMapping("/clothes")
public class PLPController extends AbstractController {

    private final PLPFacade plpFacade;

    public PLPController(PLPFacade plpFacade, ClothesFacade clothesFacade) {
        this.plpFacade = plpFacade;
    }

    @GetMapping
    private String allClothes(Model model, WebRequest webRequest) {
        PageData<ClothesPLPDto> response = plpFacade.findAll(webRequest);
        List<ClothesPLPDto> clothesPLPDtoList = response.getItems();
        model.addAttribute("createUrl", "/clothes/all");
        model.addAttribute("cardHeader", "All Clothes");
        model.addAttribute("pageData", response);
        model.addAttribute("clothes", clothesPLPDtoList);
        return "pages/open/plp";
    }

    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        return findAllRedirect(request, model, "clothes");
    }

//    @GetMapping("/details/{id}")
//    public String details(@PathVariable Long id, Model model) {
//        ClothesResponseDto dto = plpFacade.findById(id);
//        model.addAttribute("thing", dto);
//        return "pages/open/clothes/clothes_details";
//    }

//    @GetMapping("/suggestions")
//    private @ResponseBody List<String> allSearchBooks(@RequestParam String query) {
//        return plpFacade.searchBookName(query);
//    }
//
//    @PostMapping("/search")
//    private String searchBooks(@RequestParam String query, RedirectAttributes ra) {
//        ra.addAttribute(WebUtil.SEARCH_BOOK_PARAM, query);
//        return "redirect:/books";
//    }
}
