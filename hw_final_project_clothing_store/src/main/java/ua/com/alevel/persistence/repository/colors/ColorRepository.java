package ua.com.alevel.persistence.repository.colors;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.com.alevel.persistence.entity.colors.Color;
import ua.com.alevel.persistence.repository.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface ColorRepository extends BaseRepository<Color> {

    Optional<Color> findColorByColorName(String name);
}
