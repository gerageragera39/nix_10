package ua.com.alevel.service.clothes;

import ua.com.alevel.persistence.entity.clothes.Clothes;
import ua.com.alevel.service.BaseService;
import ua.com.alevel.web.dto.response.clothes.ClothesResponseDto;

import java.util.List;

public interface ClothesService extends BaseService<Clothes> {

    List<Clothes> findAllByBrandId(Long id);
}
