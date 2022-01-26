package ua.com.alevel.facade.brands.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.exception.NotUniqueException;
import ua.com.alevel.facade.brands.BrandFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.brands.Brand;
import ua.com.alevel.persistence.entity.brands.Logo;
import ua.com.alevel.service.brand.BrandService;
import ua.com.alevel.service.brand.LogoService;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.web.dto.request.PageAndSizeData;
import ua.com.alevel.web.dto.request.SortData;
import ua.com.alevel.web.dto.request.brands.BrandsRequestDto;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.brands.BrandResponseDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BrandFacadeImpl implements BrandFacade {

    private final BrandService brandService;
    private final LogoService logoService;

    public BrandFacadeImpl(BrandService brandService, LogoService logoService) {
        this.brandService = brandService;
        this.logoService = logoService;
    }

    @Override
    public void create(BrandsRequestDto brandsRequestDto) {
        Brand brand = new Brand();
        brand.setName(brandsRequestDto.getName());

        if (!brandService.existByName(brandsRequestDto.getName())) {
            brandService.create(brand);

            Logo logo = new Logo();
            if (StringUtils.isBlank(brandsRequestDto.getLogoUrl())) {
                logo.setUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/b/ba/Icon_None.svg/1200px-Icon_None.svg.png");
            } else {
                logo.setUrl(brandsRequestDto.getLogoUrl());
            }
            Optional<Brand> optionalBrand = brandService.findByName(brandsRequestDto.getName());
            if (optionalBrand.isPresent()) {
                Brand brand1 = optionalBrand.get();
                logo.setBrand(brand1);
                logoService.create(logo);
            }
        } else {
            throw new NotUniqueException("Name in not unique");
        }
    }

    @Override
    public void update(BrandsRequestDto dto, Long id) {
        Optional<Brand> optionalBrand = brandService.findById(id);
        if (optionalBrand.isPresent()) {
            Brand brand = optionalBrand.get();
            if (StringUtils.isNotBlank(dto.getName())) {
                brand.setName(dto.getName());
                brandService.update(brand);
            }
            if (StringUtils.isNotBlank(dto.getLogoUrl())) {
                Logo logo = brand.getLogo();
                logo.setUrl(dto.getLogoUrl());
                logoService.update(logo);
            }
        }
    }

    @Override
    public void delete(Long id) {
        brandService.delete(id);
    }

    @Override
    public BrandResponseDto findById(Long id) {
        return new BrandResponseDto(brandService.findById(id).get());
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

    @Override
    public Map<Long, String> findAll() {
        return brandService.findAll();
    }
}
