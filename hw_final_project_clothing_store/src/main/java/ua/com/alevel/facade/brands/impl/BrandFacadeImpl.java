package ua.com.alevel.facade.brands.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.brands.BrandFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.brands.Brand;
import ua.com.alevel.service.brand.BrandService;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.web.dto.request.PageAndSizeData;
import ua.com.alevel.web.dto.request.SortData;
import ua.com.alevel.web.dto.request.brands.BrandsRequestDto;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.brands.BrandResponseDto;
import ua.com.alevel.web.dto.response.clothes.ClothesResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandFacadeImpl implements BrandFacade {

    private final BrandService brandService;

    public BrandFacadeImpl(BrandService brandService) {
        this.brandService = brandService;
    }

    @Override
    public void create(BrandsRequestDto brandsRequestDto) {

    }

    @Override
    public void update(BrandsRequestDto brandsRequestDto, Long id) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public BrandResponseDto findById(Long id) {
        return null;
    }

    @Override
    public PageData<BrandResponseDto> findAll(WebRequest request) {
        PageAndSizeData pageAndSizeData = WebRequestUtil.generatePageAndSizeData(request);
        SortData sortData = WebRequestUtil.generateSortData(request);
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setSize(pageAndSizeData.getSize());
        dataTableRequest.setPage(pageAndSizeData.getPage());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setOrder(sortData.getOrder());

        DataTableResponse<Brand> dataTableResponse = brandService.findAll(dataTableRequest);
        List<BrandResponseDto> brands = dataTableResponse.getItems().stream().
                map(BrandResponseDto::new).
                collect(Collectors.toList());

        PageData<BrandResponseDto> pageData = new PageData<>();
        pageData.setItems(brands);
        pageData.setCurrentPage(pageAndSizeData.getPage());
        pageData.setPageSize(pageAndSizeData.getSize());
        pageData.setSort(sortData.getSort());
        pageData.setOrder(sortData.getOrder());
        pageData.setItemsSize(dataTableResponse.getItemsSize());
        pageData.initPaginationState();
        return pageData;
    }
}
