package ua.com.alevel.service.brand.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.brands.Brand;
import ua.com.alevel.persistence.repository.brands.BrandRepository;
import ua.com.alevel.service.brand.BrandService;

import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
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
        return null;
    }

    @Override
    public Brand findByName(String brandName) {
        return brandRepository.findBrandByName(brandName);
    }
}
