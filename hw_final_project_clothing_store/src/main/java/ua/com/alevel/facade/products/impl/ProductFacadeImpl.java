package ua.com.alevel.facade.products.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.products.ProductFacade;
import ua.com.alevel.persistence.entity.products.Product;
import ua.com.alevel.service.clothes.ClothesService;
import ua.com.alevel.service.personal.PersonalService;
import ua.com.alevel.service.products.ProductService;
import ua.com.alevel.web.dto.request.product.ProductRequestDto;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.products.ProductResponseDto;

@Service
public class ProductFacadeImpl implements ProductFacade {

    private final ProductService productService;
    private final PersonalService personalService;
    private final ClothesService clothesService;

    public ProductFacadeImpl(ProductService productService, ClothesService clothesService, PersonalService personalService, ClothesService clothesService1) {
        this.productService = productService;
        this.personalService = personalService;
        this.clothesService = clothesService1;
    }

    @Override
    public void create(ProductRequestDto dto) {
        Product product = new Product();
        product.setCount(dto.getCount());
        product.setClg(dto.getClg());
        product.setColor(dto.getColor());
        product.setSize(dto.getSize());
        product.setWear(clothesService.findById(dto.getWearId()).get());
        product.setPersonal(personalService.findById(dto.getPersonalId()).get());
        productService.create(product);
    }

    @Override
    public void update(ProductRequestDto productRequestDto, Long id) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public ProductResponseDto findById(Long id) {
        return null;
    }

    @Override
    public PageData<ProductResponseDto> findAll(WebRequest request) {
        return null;
    }
}
