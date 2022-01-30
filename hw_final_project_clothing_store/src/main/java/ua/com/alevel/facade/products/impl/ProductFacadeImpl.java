package ua.com.alevel.facade.products.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.products.ProductFacade;
import ua.com.alevel.persistence.entity.clothes.Clothes;
import ua.com.alevel.persistence.entity.products.Product;
import ua.com.alevel.persistence.entity.users.Personal;
import ua.com.alevel.persistence.repository.products.ProductRepository;
import ua.com.alevel.service.clothes.ClothesService;
import ua.com.alevel.service.personal.PersonalService;
import ua.com.alevel.service.products.ProductService;
import ua.com.alevel.util.SecurityUtil;
import ua.com.alevel.web.dto.request.product.ProductRequestDto;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.products.ProductResponseDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductFacadeImpl implements ProductFacade {

    private final ProductService productService;
    private final PersonalService personalService;
    private final ClothesService clothesService;

    public ProductFacadeImpl(ProductService productService, PersonalService personalService, ClothesService clothesService) {
        this.productService = productService;
        this.personalService = personalService;
        this.clothesService = clothesService;
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
        productService.delete(id);
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

    @Override
    public String findTotalPrice() {
        Personal personal = personalService.findByEmail(SecurityUtil.getUsername());
        List<Product> products = personal.getProducts().stream().toList();
        Double totalPrice = (double) 0;
        for (Product product : products) {
            totalPrice += (product.getWear().getPrice()) * product.getCount();
        }

        String stringPrice = totalPrice.toString();
        String[] finances = stringPrice.split("\\.");
        stringPrice = finances[0];
        if (finances[1].length() > 2) {
            finances[1] = Character.toString(finances[1].charAt(0)) + Character.toString(finances[1].charAt(1));
        }
        stringPrice += "." + finances[1];
        if (finances[1].length() < 2) {
            stringPrice += "0";
        }
        return stringPrice;
    }

    @Override
    public void redactQuantity(Long id, boolean isPlus) {
        Optional<Product> optionalProduct = productService.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            if (isPlus) {
                productService.create(product);
            } else {
                product.setCount(product.getCount() - 1);
                if (product.getCount() == 0) {
                    productService.delete(id);
                } else {
                    productService.update(product);
                }
            }
        }
    }
}
