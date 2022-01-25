package ua.com.alevel.persistence.repository.colors;

import ua.com.alevel.persistence.entity.colors.Color;
import ua.com.alevel.persistence.repository.BaseRepository;

import java.util.Optional;

public interface ColorRepository extends BaseRepository<Color> {

    Optional<Color> findColorByColorName(String name);
}
