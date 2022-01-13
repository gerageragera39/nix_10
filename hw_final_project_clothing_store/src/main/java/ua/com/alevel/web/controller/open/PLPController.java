package ua.com.alevel.web.controller.open;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.open.PLPFacade;
import ua.com.alevel.persistence.entity.clothes.Clothes;
import ua.com.alevel.persistence.repository.clothes.ClothesRepository;
import ua.com.alevel.web.dto.response.open.ClothesPLPDto;

import java.util.List;

@Controller
@RequestMapping("/clothes")
public class PLPController {

    private final PLPFacade plpFacade;
    private final ClothesRepository clothesRepository;

    public PLPController(PLPFacade plpFacade, ClothesRepository clothesRepository) {
        this.plpFacade = plpFacade;
        this.clothesRepository = clothesRepository;
    }

    @GetMapping
    private String allBooks(Model model, WebRequest webRequest) {
//        model.addAttribute("bookList", plpFacade.search(webRequest));
        List<Clothes> clothesList = clothesRepository.findAll();
        List<ClothesPLPDto> clothesPLPDtoList = clothesList.stream().toList().stream().map(ClothesPLPDto::new).toList();
        model.addAttribute("clothes", clothesPLPDtoList);
        return "pages/open/plp";
    }

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
