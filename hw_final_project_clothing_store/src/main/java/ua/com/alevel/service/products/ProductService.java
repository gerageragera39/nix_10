package ua.com.alevel.service.products;

import ua.com.alevel.persistence.entity.colors.Color;
import ua.com.alevel.persistence.entity.products.Product;
import ua.com.alevel.persistence.entity.users.Personal;
import ua.com.alevel.service.BaseService;

import java.util.List;
import java.util.Optional;

public interface ProductService extends BaseService<Product> {

    List<Product> findByPersonalEmail(Personal personal);
}
