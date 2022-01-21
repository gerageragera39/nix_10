package ua.com.alevel.persistence.repository.products;

import org.springframework.stereotype.Repository;
import ua.com.alevel.persistence.entity.products.Product;
import ua.com.alevel.persistence.entity.users.Personal;
import ua.com.alevel.persistence.repository.BaseRepository;

import java.util.List;

@Repository
public interface ProductRepository extends BaseRepository<Product> {

    List<Product> findAllByPersonal(Personal personal);
}
