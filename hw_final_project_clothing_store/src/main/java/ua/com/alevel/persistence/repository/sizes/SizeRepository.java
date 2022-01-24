package ua.com.alevel.persistence.repository.sizes;

import ua.com.alevel.persistence.entity.sizes.Size;
import ua.com.alevel.persistence.repository.BaseRepository;

import java.util.Optional;

public interface SizeRepository extends BaseRepository<Size> {

    Optional<Size> findSizeBySizeName(String name);
}
