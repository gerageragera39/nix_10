package ua.com.alevel.web.controller.open;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.com.alevel.facade.brands.BrandFacade;
import ua.com.alevel.facade.clothes.ClothesFacade;
import ua.com.alevel.facade.open.PLPFacade;
import ua.com.alevel.persistence.sex.Sexes;
import ua.com.alevel.persistence.thing_type.ThingTypes;
import ua.com.alevel.util.WebUtil;
import ua.com.alevel.web.controller.AbstractController;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.open.ClothesPLPDto;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/clothes")
public class PLPController extends AbstractController {

    private final PLPFacade plpFacade;

    public PLPController(PLPFacade plpFacade) {
        this.plpFacade = plpFacade;
    }

    @GetMapping
    private String allClothes(Model model, WebRequest webRequest, RedirectAttributes ra) {
        PageData<ClothesPLPDto> response = plpFacade.findAll(webRequest);
        List<ClothesPLPDto> clothesPLPDtoList = response.getItems();
        model.addAttribute("createUrl", "/clothes/all");
        model.addAttribute("cardHeader", "All Clothes");
        model.addAttribute("pageData", response);
        model.addAttribute("clothes", clothesPLPDtoList);
        model.addAttribute("brands", plpFacade.findAllBrands());
        model.addAttribute("types", plpFacade.findAllTypes());
        model.addAttribute("sexes", Sexes.values());
        model.addAttribute("colors", plpFacade.findAllColors());
        if(webRequest.getParameterMap().get("search_clothes") != null &&
                response.getItems().size() == 1) {
            return "redirect:/clothes/product/" + response.getItems().get(0).getId();
        }
        return "pages/open/plp";
    }

    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        return findAllRedirect(request, model, "clothes");
    }

    @GetMapping("/suggestions")
    private @ResponseBody List<String> searchClothesNames(@RequestParam String query) {
        return plpFacade.searchClothesNames(query);
    }

    @PostMapping("/search")
    private String searchBooks(@RequestParam String query, RedirectAttributes ra) {

//        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
//        ScriptEngine engine = scriptEngineManager.getEngineByName("JavaScript");
//
//        engine.put("myparam", "world");
//        Object eval = engine.eval("function hello(p) { return \"Hello, \" + p; } \n" +
//                "hello(myparam)");
//        System.out.println(eval);

        ra.addAttribute(WebUtil.SEARCH_CLOTHES_PARAM, query);
        return "redirect:/clothes";
    }

    @GetMapping("/test")
    private String test() {
        return "test";
    }
}
