package ua.com.alevel.facade.brands;

import ua.com.alevel.facade.BaseFacade;
import ua.com.alevel.web.dto.request.brands.BrandsRequestDto;
import ua.com.alevel.web.dto.response.brands.BrandResponseDto;

import java.util.Map;

public interface BrandFacade extends BaseFacade<BrandsRequestDto, BrandResponseDto> {

    Map<Long, String> findAll();
}
