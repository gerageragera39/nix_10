package ua.com.alevel.facade.clothes.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.clothes.ClothesFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.clothes.Clothes;
import ua.com.alevel.service.brand.BrandService;
import ua.com.alevel.service.clothes.ClothesService;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.web.dto.request.PageAndSizeData;
import ua.com.alevel.web.dto.request.SortData;
import ua.com.alevel.web.dto.request.clothes.ClothesRequestDto;
import ua.com.alevel.web.dto.response.clothes.ClothesResponseDto;
import ua.com.alevel.web.dto.response.PageData;

import java.util.List;
import java.util.Optional;
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
    public void update(ClothesRequestDto dto, Long id) {
        Optional<Clothes> optionalClothes = clothesService.findById(id);
        if(optionalClothes.isPresent()) {
            Clothes clothes = optionalClothes.get();
            clothes.setTitle(dto.getTitle());
            clothes.setSize(dto.getSize());
            clothes.setType(dto.getType());
            clothes.setSex(dto.getSex());
            clothes.setColor(dto.getColor());
            clothes.setDescription(dto.getDescription());
            clothes.setCompound(dto.getCompound());

            clothesService.update(clothes);
        }
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public ClothesResponseDto findById(Long id) {
        return new ClothesResponseDto(clothesService.findById(id).get());
    }

    @Override
    public PageData<ClothesResponseDto> findAll(WebRequest request) {
//        DataTableRequest dataTableRequest = WebUtil.generateDataTableRequestByWebRequest(request);
//        DataTableResponse<Clothes> tableResponse = clothesService.findAll(dataTableRequest);
//        List<ClothesResponseDto> clothes = tableResponse.getItems().stream().
//                map(ClothesResponseDto::new).
//                collect(Collectors.toList());
//
//        PageData<ClothesResponseDto> pageData = (PageData<ClothesResponseDto>) WebUtil.initPageData(tableResponse);
//        pageData.setItems(clothes);
//        return pageData;
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

    @Override
    public List<ClothesResponseDto> findAllByBrandId(Long id) {
        List<Clothes> clothes = clothesService.findAllByBrandId(id);
        List<ClothesResponseDto> clothesResponseDto = clothes.stream().
                map(ClothesResponseDto::new).
                collect(Collectors.toList());
        return clothesResponseDto;
    }
}
