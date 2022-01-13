package ua.com.alevel.facade.clothes.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.clothes.ClothesFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.clothes.Clothes;
import ua.com.alevel.service.brand.BrandService;
import ua.com.alevel.service.clothes.ClothesService;
import ua.com.alevel.util.WebUtil;
import ua.com.alevel.web.dto.request.clothes.ClothesRequestDto;
import ua.com.alevel.web.dto.response.clothes.ClothesResponseDto;
import ua.com.alevel.web.dto.response.PageData;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClothesFacadeImpl implements ClothesFacade {

    private final ClothesService clothesService;
    private final BrandService brandService;

    public ClothesFacadeImpl(ClothesService clothesService, BrandService brandService) {
        this.clothesService = clothesService;
        this.brandService = brandService;
    }

    @Override
    public void create(ClothesRequestDto clothesRequestDto) {
        Clothes clothes = new Clothes();
        clothes.setBrand(brandService.findByName(clothesRequestDto.getBrandName()));
        clothes.setImages(clothesRequestDto.getImages());
        clothes.setColor(clothesRequestDto.getColor());
        clothes.setCompound(clothesRequestDto.getCompound());
        clothes.setDescription(clothesRequestDto.getDescription());
        clothes.setSex(clothesRequestDto.getSex());
        clothes.setSize(clothesRequestDto.getSize());
        clothes.setTitle(clothesRequestDto.getTitle());
        clothes.setType(clothesRequestDto.getType());

        clothesService.create(clothes);
    }

    @Override
    public void update(ClothesRequestDto clothesRequestDto, Long id) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public ClothesResponseDto findById(Long id) {
        return null;
    }

    @Override
    public PageData<ClothesResponseDto> findAll(WebRequest request) {
        DataTableRequest dataTableRequest = WebUtil.generateDataTableRequestByWebRequest(request);
        DataTableResponse<Clothes> tableResponse = clothesService.findAll(dataTableRequest);
        List<ClothesResponseDto> books = tableResponse.getItems().stream().
                map(ClothesResponseDto::new).
                collect(Collectors.toList());

        PageData<ClothesResponseDto> pageData = (PageData<ClothesResponseDto>) WebUtil.initPageData(tableResponse);
        pageData.setItems(books);
        return pageData;
    }
}
