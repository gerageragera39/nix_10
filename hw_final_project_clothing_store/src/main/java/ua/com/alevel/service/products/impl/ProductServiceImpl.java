package ua.com.alevel.service.products.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.clothes.Clothes;
import ua.com.alevel.persistence.entity.products.Product;
import ua.com.alevel.persistence.entity.users.Personal;
import ua.com.alevel.persistence.repository.clothes.ClothesRepository;
import ua.com.alevel.persistence.repository.colors.ColorRepository;
import ua.com.alevel.persistence.repository.products.ProductRepository;
import ua.com.alevel.persistence.repository.sizes.SizeRepository;
import ua.com.alevel.persistence.repository.users.PersonalRepository;
import ua.com.alevel.service.products.ProductService;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ClothesRepository clothesRepository;
    private final CrudRepositoryHelper<Product, ProductRepository> crudRepositoryHelper;

    public ProductServiceImpl(ProductRepository productRepository, CrudRepositoryHelper<Product, ProductRepository> crudRepositoryHelper, ClothesRepository clothesRepository) {
        this.productRepository = productRepository;
        this.crudRepositoryHelper = crudRepositoryHelper;
        this.clothesRepository = clothesRepository;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void create(Product entity) {
        Personal personal = entity.getPersonal();
        List<Product> productList = personal.getProducts().stream().toList();
        boolean uniq = true;
        if (entity.getWear().getQuantity() != 0) {
            Clothes thing = entity.getWear();
            for (Product product : productList) {
                if (product.getClg().equals(entity.getClg()) &&
                        product.getColor().equals(entity.getColor()) &&
                        product.getSize().equals(entity.getSize())) {
                    if (product.getWear().getQuantity() > product.getCount()) {
                        product.setCount(product.getCount() + 1);
                        productRepository.save(product);
                    }
                    uniq = false;
                    break;
                }
            }
            if (uniq) {
                productRepository.save(entity);
                thing.setQuantity(thing.getQuantity() - 1);
                clothesRepository.save(thing);
            }
        }
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void update(Product entity) {
        crudRepositoryHelper.update(productRepository, entity);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void delete(Long id) {
        crudRepositoryHelper.delete(productRepository, id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        return crudRepositoryHelper.findById(productRepository, id);
    }

    @Override
    @Transactional(readOnly = true)
    public DataTableResponse<Product> findAll(DataTableRequest request) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findByPersonalEmail(Personal personal) {
        return productRepository.findAllByPersonal(personal);
    }
}
