package ua.com.alevel.facade.clothes.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.exception.NotUniqueException;
import ua.com.alevel.facade.clothes.ClothesFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.clothes.Clothes;
import ua.com.alevel.persistence.entity.clothes.Image;
import ua.com.alevel.persistence.entity.colors.Color;
import ua.com.alevel.persistence.entity.sizes.Size;
import ua.com.alevel.persistence.sex.Sexes;
import ua.com.alevel.persistence.thing_type.ThingTypes;
import ua.com.alevel.service.brand.BrandService;
import ua.com.alevel.service.clothes.ClothesService;
import ua.com.alevel.service.colors.ColorService;
import ua.com.alevel.service.elastic.ElasticClothesSearchService;
import ua.com.alevel.service.image.ImageService;
import ua.com.alevel.service.sizes.SizeService;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.web.dto.request.PageAndSizeData;
import ua.com.alevel.web.dto.request.SortData;
import ua.com.alevel.web.dto.request.clothes.ClothesRequestDto;
import ua.com.alevel.web.dto.response.clothes.ClothesResponseDto;
import ua.com.alevel.web.dto.response.PageData;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClothesFacadeImpl implements ClothesFacade {

    private final ClothesService clothesService;
    private final BrandService brandService;
    private final ColorService colorService;
    private final SizeService sizeService;
    private final ImageService imageService;
    private final ElasticClothesSearchService elasticClothesSearchService;

    public ClothesFacadeImpl(ClothesService clothesService, BrandService brandService, ColorService colorService, SizeService sizeService, ImageService imageService, ElasticClothesSearchService elasticClothesSearchService) {
        this.clothesService = clothesService;
        this.brandService = brandService;
        this.colorService = colorService;
        this.sizeService = sizeService;
        this.imageService = imageService;
        this.elasticClothesSearchService = elasticClothesSearchService;
    }

    @Override
    public void create(ClothesRequestDto clothesRequestDto) {
        Clothes clothes = new Clothes();
        clothes.setBrand(brandService.findByName(clothesRequestDto.getBrandName()).get());
        clothes.setCLG(clothesRequestDto.getClg());
        clothes.setCompound(clothesRequestDto.getCompound());
        clothes.setDescription(clothesRequestDto.getDescription());
        clothes.setSex(Sexes.valueOf(clothesRequestDto.getSex()));
        clothes.setTitle(clothesRequestDto.getTitle());
        clothes.setType(ThingTypes.valueOf(clothesRequestDto.getType()));

        if (!clothesService.existByClg(clothes.getCLG())) {
            clothesService.create(clothes);

            Image image = new Image();
            if (StringUtils.isBlank(clothesRequestDto.getImageUrl())) {
                image.setUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/b/ba/Icon_None.svg/1200px-Icon_None.svg.png");
            } else {
                image.setUrl(clothesRequestDto.getImageUrl());
            }
            Optional<Clothes> optionalThing = clothesService.findByClg(clothesRequestDto.getClg());
            if (optionalThing.isPresent()) {
                Clothes thing = optionalThing.get();
                image.setThing(thing);
                imageService.create(image);
            }
        } else {
            throw new NotUniqueException("CLG is not unique");
        }
    }

    @Override
    public void update(ClothesRequestDto dto, Long id) {
        Optional<Clothes> optionalClothes = clothesService.findById(id);
        if (optionalClothes.isPresent()) {
            Clothes clothes = optionalClothes.get();
            if (StringUtils.isNotBlank(dto.getTitle())) {
                clothes.setTitle(dto.getTitle());
            }
            if (StringUtils.isNotBlank(dto.getCompound())) {
                clothes.setCompound(dto.getCompound());
            }
            if (StringUtils.isNotBlank(dto.getDescription())) {
                clothes.setDescription(dto.getDescription());
            }
            if (!dto.getSex().equals("Change sex")) {
                clothes.setSex(Sexes.valueOf(dto.getSex()));
            }
            if (!dto.getType().equals("Change type")) {
                clothes.setType(ThingTypes.valueOf(dto.getType()));
            }
            clothesService.update(clothes);
        }
    }

    @Override
    public Map<Long, String> findColorsByThingId(Long id) {
        List<Color> colors = clothesService.findById(id).get().getColors().stream().toList();
        Map<Long, String> map = new HashMap<>();
        for (int i = 0; i < colors.size(); i++) {
            map.put(colors.get(i).getId(), colors.get(i).getColorName());
        }
        return map;
    }

    @Override
    public List<String> findAllColorsByThingId(Long id) {
        List<Color> colors = clothesService.findById(id).get().getColors().stream().toList();
        List<String> names = new ArrayList<>();
        for (Color color : colors) {
            names.add(color.getColorName());
        }
        return names;
    }


    @Override
    public List<String> findAllColorsNotByThingId(Long id) {
        List<Color> allColors = colorService.findAll();
        List<Color> addedColors = clothesService.findById(id).get().getColors().stream().toList();
        List<Color> notAddedColors = new ArrayList<>();
        notAddedColors.addAll(allColors);
        for (int i = 0; i < allColors.size(); i++) {
            for (int j = 0; j < addedColors.size(); j++) {
                if (allColors.get(i).getId() == addedColors.get(j).getId()) {
                    notAddedColors.remove(allColors.get(i));
                    break;
                }
            }
        }
        List<String> names = new ArrayList<>();
        for (Color notAddedColor : notAddedColors) {
            names.add(notAddedColor.getColorName());
        }
        return names;
    }

    @Override
    public Map<Long, String> findSizesByThingId(Long id) {
        List<Size> sizes = clothesService.findById(id).get().getSizes().stream().toList();
        Map<Long, String> map = new HashMap<>();
        for (int i = 0; i < sizes.size(); i++) {
            map.put(sizes.get(i).getId(), sizes.get(i).getSizeName());
        }
        return map;
    }

    @Override
    public List<String> searchClothesNames(String query) {
        return elasticClothesSearchService.searchClothesNames(query);
    }

    @Override
    public void updateColor(ClothesRequestDto dto, Long id) {
        Optional<Clothes> optionalClothes = clothesService.findById(id);
        if (optionalClothes.isPresent()) {
            Clothes clothes = optionalClothes.get();
            if (!dto.getColor().equals("Add color")) {
                Color color = colorService.findByName(dto.getColor());
                color.addThing(clothes);
                colorService.update(color);
            }
            if (!dto.getRemoveColor().equals("Remove color")) {
                Color color = colorService.findByName(dto.getRemoveColor());
                color.removeThing(clothes);
                colorService.update(color);
            }
        }
    }

    @Override
    public List<String> findAllSizesByThingId(Long id) {
        List<Size> sizes = clothesService.findById(id).get().getSizes().stream().toList();
        List<String> names = new ArrayList<>();
        for (Size size : sizes) {
            names.add(size.getSizeName());
        }
        return names;
    }

    @Override
    public List<String> findAllSizesNotByThingId(Long id) {
        List<Size> allSizes = sizeService.findAll();
        List<Size> addedSizes = clothesService.findById(id).get().getSizes().stream().toList();
        List<Size> notAddedSizes = new ArrayList<>();
        notAddedSizes.addAll(allSizes);
        for (int i = 0; i < allSizes.size(); i++) {
            for (int j = 0; j < addedSizes.size(); j++) {
                if (allSizes.get(i).getId() == addedSizes.get(j).getId()) {
                    notAddedSizes.remove(allSizes.get(i));
                    break;
                }
            }
        }
        List<String> names = new ArrayList<>();
        for (Size notAddedSize : notAddedSizes) {
            names.add(notAddedSize.getSizeName());
        }
        return names;
    }

    @Override
    public void updateSize(ClothesRequestDto dto, Long id) {
        Optional<Clothes> optionalClothes = clothesService.findById(id);
        if (optionalClothes.isPresent()) {
            Clothes clothes = optionalClothes.get();
            if (!dto.getSize().equals("Add size")) {
                Size size = sizeService.findByName(dto.getSize());
                size.addThing(clothes);
                sizeService.update(size);
            }
            if (!dto.getRemoveSize().equals("Remove size")) {
                Size size = sizeService.findByName(dto.getRemoveSize());
                size.removeThing(clothes);
                sizeService.update(size);
            }
        }
    }

    @Override
    public void delete(Long id) {
        clothesService.delete(id);
    }

    @Override
    public ClothesResponseDto findById(Long id) {
        Optional<Clothes> optionalClothes = clothesService.findById(id);
        if (optionalClothes.isPresent()) {
            return new ClothesResponseDto(optionalClothes.get());
        }
        return new ClothesResponseDto();
    }

    @Override
    public PageData<ClothesResponseDto> findAll(WebRequest request) {
        PageAndSizeData pageAndSizeData = WebRequestUtil.generatePageAndSizeData(request);
        SortData sortData = WebRequestUtil.generateSortData(request);
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setSize(pageAndSizeData.getSize());
        dataTableRequest.setPage(pageAndSizeData.getPage());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setOrder(sortData.getOrder());

        DataTableResponse<Clothes> dataTableResponse = clothesService.findAll(dataTableRequest);
        List<ClothesResponseDto> clothes = dataTableResponse.getItems().stream().
                map(ClothesResponseDto::new).
                collect(Collectors.toList());

        PageData<ClothesResponseDto> pageData = new PageData<>();
        pageData.setItems(clothes);
        pageData.setCurrentPage(pageAndSizeData.getPage());
        pageData.setPageSize(pageAndSizeData.getSize());
        pageData.setSort(sortData.getSort());
        pageData.setOrder(sortData.getOrder());
        pageData.setItemsSize(dataTableResponse.getItemsSize());
        pageData.initPaginationState();
        return pageData;
    }

    @Override
    public PageData<ClothesResponseDto> personalFindAll(WebRequest request) {
        PageAndSizeData pageAndSizeData = WebRequestUtil.generatePersonalPageAndSizeData(request);
        SortData sortData = WebRequestUtil.generateSortData(request);
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setSize(pageAndSizeData.getSize());
        dataTableRequest.setPage(pageAndSizeData.getPage());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setOrder(sortData.getOrder());

        DataTableResponse<Clothes> dataTableResponse = clothesService.findAll(dataTableRequest);
        List<ClothesResponseDto> clothes = dataTableResponse.getItems().stream().
                map(ClothesResponseDto::new).
                collect(Collectors.toList());

        PageData<ClothesResponseDto> pageData = new PageData<>();
        pageData.setItems(clothes);
        pageData.setCurrentPage(pageAndSizeData.getPage());
        pageData.setPageSize(pageAndSizeData.getSize());
        pageData.setSort(sortData.getSort());
        pageData.setOrder(sortData.getOrder());
        pageData.setItemsSize(dataTableResponse.getItemsSize());
        pageData.initPaginationState();
        return pageData;
    }
}
