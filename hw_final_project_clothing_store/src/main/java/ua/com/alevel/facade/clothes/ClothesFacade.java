package ua.com.alevel.facade.clothes;

import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.BaseFacade;
import ua.com.alevel.web.dto.request.clothes.ClothesRequestDto;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.clothes.ClothesResponseDto;

import java.util.List;
import java.util.Map;

public interface ClothesFacade extends BaseFacade<ClothesRequestDto, ClothesResponseDto> {

    PageData<ClothesResponseDto> personalFindAll(WebRequest request);

    List<ClothesResponseDto> findAllByBrandId(Long id);

    void update(ClothesRequestDto dto, ClothesResponseDto tempDto, Long id);

    Map<Long, String> findColorsByThingId(Long id);

    Map<Long, String> findSizesByThingId(Long id);
}
