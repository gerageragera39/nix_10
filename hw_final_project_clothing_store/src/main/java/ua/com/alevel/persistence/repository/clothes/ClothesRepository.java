package ua.com.alevel.persistence.repository.clothes;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.alevel.persistence.entity.clothes.Clothes;
import ua.com.alevel.persistence.entity.colors.Color;
import ua.com.alevel.persistence.entity.sizes.Size;
import ua.com.alevel.persistence.repository.BaseRepository;
import ua.com.alevel.persistence.sex.Sexes;
import ua.com.alevel.persistence.thing_type.ThingTypes;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClothesRepository extends BaseRepository<Clothes> {

    Optional<Clothes> findClothesByCLG(String CLG);

    @Query("select c.colors from Clothes c where c.id = :id and c.visible = true ")
    List<Color> findColorsByThingId(@Param("id") Long id);

    @Query("select c.title from Clothes c where c.visible = true and c.visible = true ")
    List<String> findAllNames(Pageable pageable);

    List<Clothes> findAllByVisibleTrue(Pageable pageable);

    @Query("select c.id from Clothes c where c.title like :search and c.visible = true ")
    List<Long> findAllClothesIdByTitleContainingAndVisibleTrue(@Param("search")String search);

    @Query("select c.id from Clothes c where c.brand.id = :id and c.visible = true ")
    List<Long> findAllClothesIdByBrandIdAndVisibleTrue(@Param("id") Long id);

    @Query("select c.id from Clothes c where c.sex = :sex and c.visible = true ")
    List<Long> findAllClothesIdBySexEqualsAndVisibleTrue(@Param("sex")Sexes sex);

    @Query("select c.id from Clothes c where c.type = :type and c.visible = true ")
    List<Long> findAllClothesIdByTypeEqualsAndVisibleTrue(@Param("type")ThingTypes type);

    boolean existsByCLG(String clg);
}
