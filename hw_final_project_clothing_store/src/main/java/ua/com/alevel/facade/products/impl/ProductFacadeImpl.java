package ua.com.alevel.facade.products.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.products.ProductFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.clothes.Clothes;
import ua.com.alevel.persistence.entity.products.Product;
import ua.com.alevel.persistence.entity.users.Personal;
import ua.com.alevel.service.clothes.ClothesService;
import ua.com.alevel.service.personal.PersonalService;
import ua.com.alevel.service.products.ProductService;
import ua.com.alevel.util.WebUtil;
import ua.com.alevel.web.dto.request.product.ProductRequestDto;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.open.ClothesPLPDto;
import ua.com.alevel.web.dto.response.products.ProductResponseDto;

import java.util.List;
import java.util.stream.Collectors;

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
        product.setPersonal(personalService.findByEmail(dto.getPersonalEmail()));
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

    @Override
    public List<ProductResponseDto> findByPersonalEmail(String email) {
        List<Product> productList = productService.findByPersonalEmail(personalService.findByEmail(email));
        List<ProductResponseDto> products = productList.stream().
                map(ProductResponseDto::new).
                collect(Collectors.toList());

       return products;
    }
}
