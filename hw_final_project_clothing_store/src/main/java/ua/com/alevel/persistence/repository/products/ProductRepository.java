package ua.com.alevel.persistence.repository.products;

import org.springframework.stereotype.Repository;
import ua.com.alevel.persistence.entity.products.Product;
import ua.com.alevel.persistence.repository.BaseRepository;

@Repository
public interface ProductRepository extends BaseRepository<Product> {
}
