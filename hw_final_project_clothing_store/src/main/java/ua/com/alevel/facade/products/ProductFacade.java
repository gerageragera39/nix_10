package ua.com.alevel.facade.products;

import ua.com.alevel.facade.BaseFacade;
import ua.com.alevel.persistence.entity.users.Personal;
import ua.com.alevel.web.dto.request.product.ProductRequestDto;
import ua.com.alevel.web.dto.response.products.ProductResponseDto;

import java.util.List;

public interface ProductFacade extends BaseFacade<ProductRequestDto, ProductResponseDto> {

    List<ProductResponseDto> findByPersonalEmail(String email);

    String findTotalPrice();

    void redactQuantity(Long id, boolean isPlus);
}
