package ua.com.alevel.web.controller.open;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.com.alevel.facade.open.PLPFacade;
import ua.com.alevel.persistence.sex.Sexes;
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

    private final HeaderName[] columnNames = new HeaderName[]{
            new HeaderName("price", "price", "price")
    };

    public PLPController(PLPFacade plpFacade) {
        this.plpFacade = plpFacade;
    }

    @GetMapping
    private String allClothes(Model model, WebRequest webRequest, RedirectAttributes ra) {
        PageData<ClothesPLPDto> response = plpFacade.findAll(webRequest);
        List<ClothesPLPDto> clothesPLPDtoList = response.getItems();
        initDataTable(response, columnNames, model);
        model.addAttribute("createUrl", "/clothes/all");
        model.addAttribute("cardHeader", "All Clothes");
        model.addAttribute("pageData", response);
        model.addAttribute("clothes", clothesPLPDtoList);
        model.addAttribute("brands", plpFacade.findAllBrands());
        model.addAttribute("types", plpFacade.findAllTypes());
        model.addAttribute("sexes", Sexes.values());
        model.addAttribute("colors", plpFacade.findAllColors());
        model.addAttribute("sizes", plpFacade.findAllSizes());
        if (webRequest.getParameterMap().get("search_clothes") != null &&
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
    private @ResponseBody
    List<String> searchClothesNames(@RequestParam String query) {
        return plpFacade.searchClothesNames(query);
    }

    @PostMapping("/search")
    private String searchBooks(@RequestParam String query, WebRequest webRequest, RedirectAttributes ra) {
        String referrer = webRequest.getHeader("referer");
        System.out.println("referrer = " + referrer);
        String[] url = referrer.split("\\?");
        if(url.length == 2) {
            if(url[1].charAt(0) == '&') {
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
        ra.addAttribute(WebUtil.SEARCH_CLOTHES_PARAM, query);
        return "redirect:/clothes";
    }

    @GetMapping("/test")
    private String test() {
        return "test";
    }
}
