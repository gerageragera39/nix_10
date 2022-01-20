package ua.com.alevel.facade.products;

import ua.com.alevel.facade.BaseFacade;
import ua.com.alevel.web.dto.request.product.ProductRequestDto;
import ua.com.alevel.web.dto.response.products.ProductResponseDto;

public interface ProductFacade extends BaseFacade<ProductRequestDto, ProductResponseDto> {
}
