package ua.com.alevel.service.brand.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.brands.Brand;
import ua.com.alevel.persistence.entity.clothes.Clothes;
import ua.com.alevel.persistence.repository.brands.BrandRepository;
import ua.com.alevel.service.brand.BrandService;
import ua.com.alevel.util.WebResponseUtil;

import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final CrudRepositoryHelper<Brand, BrandRepository> crudRepositoryHelper;

    public BrandServiceImpl(BrandRepository brandRepository, CrudRepositoryHelper<Brand, BrandRepository> crudRepositoryHelper) {
        this.brandRepository = brandRepository;
        this.crudRepositoryHelper = crudRepositoryHelper;
    }

    @Override
    public void create(Brand entity) {

    }

    @Override
    public void update(Brand entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Optional<Brand> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public DataTableResponse<Brand> findAll(DataTableRequest request) {
        DataTableResponse<Brand> dataTableResponse = crudRepositoryHelper.findAll(brandRepository, request);
        long count = brandRepository.countAllByVisibleTrue();
        WebResponseUtil.initDataTableResponse(request, dataTableResponse, count);
        return dataTableResponse;
    }

    @Override
    public Brand findByName(String brandName) {
        return brandRepository.findBrandByName(brandName);
    }
}
