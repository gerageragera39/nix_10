package ua.com.alevel.persistence.repository.clothes;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.alevel.persistence.entity.clothes.Clothes;
import ua.com.alevel.persistence.entity.colors.Color;
import ua.com.alevel.persistence.repository.BaseRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClothesRepository extends BaseRepository<Clothes> {

    Optional<Clothes> findClothesByCLG(String CLG);

    List<Clothes> findAllByVisible(Boolean visible, Pageable pageable);

    List<Clothes> findAllByBrandId(Long id, Pageable pageable);

    @Query("select c.colors from Clothes c where c.id = :id")
    List<Color> findColorsByThingId(@Param("id") Long id);

    @Query("select c.title from Clothes c")
    List<String> findAllNames(Pageable pageable);

    List<Clothes> findAllByVisibleTrue(Pageable pageable);

    List<Clothes> findAllByTitleContaining(String search, Pageable pageable);

    List<Clothes> findAllByBrandIdAndTitleContaining(Long id, String search, Pageable pageable);
}
