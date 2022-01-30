package ua.com.alevel.service.clothes;

import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.clothes.Clothes;
import ua.com.alevel.persistence.entity.colors.Color;
import ua.com.alevel.service.BaseService;
import ua.com.alevel.web.dto.response.clothes.ClothesResponseDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ClothesService extends BaseService<Clothes> {

    Map<Long, String> findColorsByThingId(Long id);

    List<Color> findAllColors();

    Optional<Clothes> findByClg(String clg);

    boolean existByClg(String clg);

    List<Clothes> findAll();
}
