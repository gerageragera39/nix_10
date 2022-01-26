package ua.com.alevel.persistence.repository.brands;

import org.springframework.stereotype.Repository;
import ua.com.alevel.persistence.entity.brands.Brand;
import ua.com.alevel.persistence.repository.BaseRepository;

import java.util.Optional;

@Repository
public interface BrandRepository extends BaseRepository<Brand> {

    Optional<Brand> findBrandByName(String name);

    boolean existsByName(String name);
}
