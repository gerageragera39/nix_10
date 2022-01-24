package ua.com.alevel.facade.open;

import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.BaseFacade;
import ua.com.alevel.persistence.entity.clothes.Clothes;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.ResponseDto;
import ua.com.alevel.web.dto.response.clothes.ClothesResponseDto;

import java.util.List;
import java.util.Map;

public interface PLPFacade<RES extends ResponseDto>{

    PageData<RES> findAll(WebRequest request);

    ClothesResponseDto findById(Long id);

    List<String> searchClothesNames(String query);

    Map<Long, String> findAllBrands();

    Map<Long, String> findAllColors();

    Map<Long, String> findAllTypes();

    Map<Long, String> findAllSizes();
}
