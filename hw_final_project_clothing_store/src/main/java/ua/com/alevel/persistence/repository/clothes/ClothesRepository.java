package ua.com.alevel.persistence.repository.clothes;

import org.springframework.stereotype.Repository;
import ua.com.alevel.persistence.entity.clothes.Clothes;
import ua.com.alevel.persistence.repository.BaseRepository;

import java.util.Optional;

@Repository
public interface ClothesRepository extends BaseRepository<Clothes> {

    Optional<Clothes> findClothesByCLG(String CLG);
}
