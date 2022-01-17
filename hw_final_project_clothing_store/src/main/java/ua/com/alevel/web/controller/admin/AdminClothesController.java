package ua.com.alevel.web.controller.admin;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.com.alevel.facade.clothes.ClothesFacade;
import ua.com.alevel.persistence.colors.Color;
import ua.com.alevel.persistence.sex.Sexes;
import ua.com.alevel.persistence.sizes.Sizes;
import ua.com.alevel.persistence.thing_type.ThingTypes;
import ua.com.alevel.web.controller.AbstractController;
import ua.com.alevel.web.dto.request.clothes.ClothesRequestDto;
import ua.com.alevel.web.dto.response.clothes.ClothesResponseDto;
import ua.com.alevel.web.dto.response.PageData;

@Controller
@RequestMapping("/admin/clothes")
public class AdminClothesController extends AbstractController {

    private String tempTitle;
    private String tempDescription;
    private String tempCompound;

    private final HeaderName[] columnNames = new HeaderName[] {
            new HeaderName("#", null, null),
            new HeaderName("image", null, null),
            new HeaderName("title", "title", "title"),
            new HeaderName("color", "color", "color"),
            new HeaderName("size", "size", "size"),
            new HeaderName("sex", "sex", "sex"),
            new HeaderName("type", "type", "type"),
            new HeaderName("created", "created", "created"),
            new HeaderName("price", "price", "price"),
            new HeaderName("quantity", "quantity", "quantity"),
            new HeaderName("details", null, null),
            new HeaderName("delete", null, null)
    };

    private final ClothesFacade clothesFacade;

    public AdminClothesController(ClothesFacade clothesFacade) {
        this.clothesFacade = clothesFacade;
    }

    @GetMapping
    public String findAll(Model model, WebRequest request) {
        PageData<ClothesResponseDto> response = clothesFacade.findAll(request);
        initDataTable(response, columnNames, model);
        model.addAttribute("createUrl", "/admin/clothes/all");
//        model.addAttribute("createUrl", "/admin/clothes");
        model.addAttribute("createNew", "/admin/clothes/new");
        model.addAttribute("cardHeader", "All Clothes");
        return "pages/admin/clothes/clothes_all";
    }

    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        return findAllRedirect(request, model, "admin/clothes");
    }

    @GetMapping("/new")
    public String redirectToNewBookPage(Model model) {
        model.addAttribute("book", new ClothesResponseDto());
        return "pages/admin/clothes/clothes_new";
    }

    @PostMapping("/create")
    public String createNewDepartment(RedirectAttributes attributes, @ModelAttribute("thing") ClothesRequestDto dto, @RequestParam("file") MultipartFile file) {
        clothesFacade.create(dto);
        return "redirect:/admin/clothes";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        clothesFacade.delete(id);
        return "redirect:/admin/clothes";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Long id, Model model) {
        ClothesResponseDto dto = clothesFacade.findById(id);
        model.addAttribute("thing", dto);
        return "pages/admin/clothes/clothes_details";
    }

    @GetMapping("/to/update/{id}")
    public String toUpdate(@PathVariable Long id, Model model) {
        ClothesResponseDto dto = clothesFacade.findById(id);
        setTempTitle(dto.getTitle());
        setTempDescription(dto.getDescription());
        setTempCompound(dto.getCompound());
        model.addAttribute("id", id);
        model.addAttribute("thing", new ClothesRequestDto());
        model.addAttribute("colors", Color.values());
        model.addAttribute("sizes", Sizes.values());
        model.addAttribute("sexes", Sexes.values());
        model.addAttribute("types", ThingTypes.values());
        return "pages/admin/clothes/clothes_update";
    }

    @PostMapping("/update/{id}")
    public String updateThing(@ModelAttribute("thing") ClothesRequestDto dto, @PathVariable Long id, Model model) {
        if(StringUtils.isBlank(dto.getTitle())) {
            dto.setTitle(getTempTitle());
        }
        if(StringUtils.isBlank(dto.getCompound())) {
            dto.setCompound(getTempCompound());
        }
        if(StringUtils.isBlank(dto.getDescription())) {
            dto.setDescription(getTempDescription());
        }
        clothesFacade.update(dto, id);
        model.addAttribute("thing", clothesFacade.findById(id));
//        return "pages/admin/clothes/clothes_details";
        return "redirect:/admin/clothes/details/" + id;
    }

    public String getTempTitle() {
        return tempTitle;
    }

    public void setTempTitle(String tempTitle) {
        this.tempTitle = tempTitle;
    }

    public String getTempDescription() {
        return tempDescription;
    }

    public void setTempDescription(String tempDescription) {
        this.tempDescription = tempDescription;
    }

    public String getTempCompound() {
        return tempCompound;
    }

    public void setTempCompound(String tempCompound) {
        this.tempCompound = tempCompound;
    }
}
