package ua.com.alevel.service.brand.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.brands.Brand;
import ua.com.alevel.persistence.entity.clothes.Clothes;
import ua.com.alevel.persistence.repository.brands.BrandRepository;
import ua.com.alevel.persistence.repository.clothes.ClothesRepository;
import ua.com.alevel.persistence.repository.clothes.ImageRepository;
import ua.com.alevel.service.brand.BrandService;
import ua.com.alevel.service.clothes.ClothesService;
import ua.com.alevel.util.WebResponseUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final ClothesService clothesService;
    private final CrudRepositoryHelper<Brand, BrandRepository> crudRepositoryHelper;

    public BrandServiceImpl(BrandRepository brandRepository, ClothesService clothesService, CrudRepositoryHelper<Brand, BrandRepository> crudRepositoryHelper) {
        this.brandRepository = brandRepository;
        this.clothesService = clothesService;
        this.crudRepositoryHelper = crudRepositoryHelper;
    }

    @Override
    public void create(Brand entity) {
        crudRepositoryHelper.create(brandRepository, entity);
    }

    @Override
    public void update(Brand entity) {

    }

    @Override
    public void delete(Long id) {
        Optional<Brand> optionalBrand = crudRepositoryHelper.findById(brandRepository, id);
        if(optionalBrand.isPresent()) {
            Brand brand = optionalBrand.get();
            List<Clothes> clothes = brand.getClothes().stream().toList();
            for (Clothes thing : clothes) {
                clothesService.delete(thing.getId());
            }
            crudRepositoryHelper.delete(brandRepository, id);
        }
    }

    @Override
    public Optional<Brand> findById(Long id) {
        return crudRepositoryHelper.findById(brandRepository, id);
    }

    @Override
    public DataTableResponse<Brand> findAll(DataTableRequest request) {
        DataTableResponse<Brand> dataTableResponse = crudRepositoryHelper.findAll(brandRepository, request);
        long count = brandRepository.countAllByVisibleTrue();
        WebResponseUtil.initDataTableResponse(request, dataTableResponse, count);
        return dataTableResponse;
    }

    @Override
    public Optional<Brand> findByName(String brandName) {
        return brandRepository.findBrandByName(brandName);
    }

    @Override
    public Map<Long, String> findAll() {
        List<Brand> brands = brandRepository.findAll();
        Map<Long, String> map = new HashMap<>();
        for (Brand brand : brands) {
            map.put(brand.getId(), brand.getName());
        }
        return map;
    }
}
