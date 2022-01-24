package ua.com.alevel.persistence.repository.brands;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.alevel.persistence.entity.brands.Brand;
import ua.com.alevel.persistence.repository.BaseRepository;

import java.util.List;

@Repository
public interface BrandRepository extends BaseRepository<Brand> {

    Brand findBrandByName(String name);
}
