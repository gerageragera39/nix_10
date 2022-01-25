package ua.com.alevel.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.facade.brands.BrandFacade;
import ua.com.alevel.facade.clothes.ClothesFacade;
import ua.com.alevel.facade.clothes.ImageFacade;
import ua.com.alevel.persistence.sex.Sexes;
import ua.com.alevel.persistence.thing_type.ThingTypes;
import ua.com.alevel.web.controller.AbstractController;
import ua.com.alevel.web.dto.request.clothes.ClothesRequestDto;
import ua.com.alevel.web.dto.request.clothes.ImageRequestDto;
import ua.com.alevel.web.dto.response.clothes.ClothesResponseDto;
import ua.com.alevel.web.dto.response.PageData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin/clothes")
public class AdminClothesController extends AbstractController {

    private final HeaderName[] columnNames = new HeaderName[]{
            new HeaderName("#", null, null),
            new HeaderName("image", null, null),
            new HeaderName("title", "title", "title"),
            new HeaderName("sex", "sex", "sex"),
            new HeaderName("type", "type", "type"),
            new HeaderName("created", "created", "created"),
            new HeaderName("price", "price", "price"),
            new HeaderName("quantity", "quantity", "quantity"),
            new HeaderName("details", null, null),
            new HeaderName("delete", null, null)
    };

    private final ClothesFacade clothesFacade;
    private final ImageFacade imageFacade;
    private final BrandFacade brandFacade;

    public AdminClothesController(ClothesFacade clothesFacade, ImageFacade imageFacade, BrandFacade brandFacade) {
        this.clothesFacade = clothesFacade;
        this.imageFacade = imageFacade;
        this.brandFacade = brandFacade;
    }

    @GetMapping
    public String findAll(Model model, WebRequest request) {
        PageData<ClothesResponseDto> response = clothesFacade.findAll(request);
        initDataTable(response, columnNames, model);
        model.addAttribute("createUrl", "/admin/clothes/all");
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
        model.addAttribute("thing", new ClothesRequestDto());
        model.addAttribute("sexes", Sexes.values());
        model.addAttribute("types", ThingTypes.values());
        model.addAttribute("brands", brandFacade.findAll());
        return "pages/admin/clothes/clothes_new";
    }

    @PostMapping("/create")
    public String createNewDepartment(@ModelAttribute("thing") ClothesRequestDto dto) {
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
        model.addAttribute("colors", clothesFacade.findColorsByThingId(id));
        model.addAttribute("sizes", clothesFacade.findSizesByThingId(id));
        model.addAttribute("thing", dto);
        return "pages/admin/clothes/clothes_details";
    }

    @GetMapping("/to/update/{id}")
    public String toUpdate(@PathVariable Long id, Model model) {
        generateFields(model);
        model.addAttribute("id", id);
        model.addAttribute("thing", new ClothesRequestDto());
        return "pages/admin/clothes/clothes_update";
    }

    @PostMapping("/update/{id}")
    public String updateThing(@ModelAttribute("thing") ClothesRequestDto dto, @PathVariable Long id, Model model) {
        clothesFacade.update(dto, id);
        return "redirect:/admin/clothes/details/" + id;
    }

    @GetMapping("/color/to/update/{id}")
    public String colorToUpdate(@PathVariable Long id, Model model) {
        generateColorsList(id, model);
        model.addAttribute("id", id);
        model.addAttribute("thing", new ClothesRequestDto());
        return "pages/admin/clothes/color_update";
    }

    @PostMapping("color/update/{id}")
    public String colorUpdate(@ModelAttribute("thing") ClothesRequestDto dto, @PathVariable Long id, Model model) {
        clothesFacade.updateColor(dto, id);
        return "redirect:/admin/clothes/details/" + id;
    }

    @GetMapping("/size/to/update/{id}")
    public String sizeToUpdate(@PathVariable Long id, Model model) {
        generateSizesList(id, model);
        model.addAttribute("id", id);
        model.addAttribute("thing", new ClothesRequestDto());
        return "pages/admin/clothes/size_update";
    }

    @PostMapping("size/update/{id}")
    public String sizeUpdate(@ModelAttribute("thing") ClothesRequestDto dto, @PathVariable Long id, Model model) {
        clothesFacade.updateSize(dto, id);
        return "redirect:/admin/clothes/details/" + id;
    }

    @GetMapping("/images/to/add/{id}")
    public String toAddImage(@PathVariable Long id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("image", new ImageRequestDto());
        return "pages/admin/clothes/images_new";
    }

    @PostMapping("/images/add/{id}")
    public String addImage(@ModelAttribute("image") ImageRequestDto dto, @PathVariable Long id) {
        dto.setThingId(id);
        imageFacade.create(dto);
        return "redirect:/admin/clothes/details/" + id;
    }

    @GetMapping("{thingId}/images/delete/{imageId}")
    public String deleteImage(@PathVariable Long thingId, @PathVariable Long imageId) {
        imageFacade.delete(imageId);
        return "redirect:/admin/clothes/details/" + thingId;
    }

    private void generateFields(Model model) {
        List<Object> colors = new ArrayList<>();
        List<Object> sizes = new ArrayList<>();
        List<Object> sexes = new ArrayList<>();
        sexes.add("Change sex");
        sexes.addAll(Arrays.stream(Sexes.values()).toList());
        List<Object> types = new ArrayList<>();
        types.add("Change type");
        types.addAll(Arrays.stream(ThingTypes.values()).toList());
        model.addAttribute("colors", colors);
        model.addAttribute("sizes", sizes);
        model.addAttribute("sexes", sexes);
        model.addAttribute("types", types);
    }

    private void generateColorsList(Long id, Model model) {
        List<String> added = new ArrayList<>();
        added.add("Remove color");
        added.addAll(clothesFacade.findAllColorsByThingId(id));
        List<String> notAdded = new ArrayList<>();
        notAdded.add("Add color");
        notAdded.addAll(clothesFacade.findAllColorsNotByThingId(id));
        model.addAttribute("added", added);
        model.addAttribute("notAdded", notAdded);
    }

    private void generateSizesList(Long id, Model model) {
        List<String> added = new ArrayList<>();
        added.add("Remove size");
        added.addAll(clothesFacade.findAllSizesByThingId(id));
        List<String> notAdded = new ArrayList<>();
        notAdded.add("Add size");
        notAdded.addAll(clothesFacade.findAllSizesNotByThingId(id));
        model.addAttribute("added", added);
        model.addAttribute("notAdded", notAdded);
    }
}
