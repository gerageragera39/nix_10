package ua.com.alevel.service.products.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.products.Product;
import ua.com.alevel.persistence.entity.users.Personal;
import ua.com.alevel.persistence.repository.colors.ColorRepository;
import ua.com.alevel.persistence.repository.products.ProductRepository;
import ua.com.alevel.persistence.repository.sizes.SizeRepository;
import ua.com.alevel.persistence.repository.users.PersonalRepository;
import ua.com.alevel.service.products.ProductService;
import ua.com.alevel.util.SecurityUtil;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final PersonalRepository personalRepository;
    private final CrudRepositoryHelper<Product, ProductRepository> crudRepositoryHelper;

    public ProductServiceImpl(ProductRepository productRepository, CrudRepositoryHelper<Product, ProductRepository> crudRepositoryHelper, ColorRepository colorRepository, SizeRepository sizeRepository, PersonalRepository personalRepository) {
        this.productRepository = productRepository;
        this.crudRepositoryHelper = crudRepositoryHelper;
        this.personalRepository = personalRepository;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void create(Product entity) {
        Personal personal = personalRepository.findByEmail(SecurityUtil.getUsername());
        List<Product> productList = personal.getProducts().stream().toList();
        boolean uniq = true;
        for (Product product : productList) {
            if (product.getClg().equals(entity.getClg()) &&
                    product.getColor().equals(entity.getColor()) &&
                    product.getSize().equals(entity.getSize())) {
                product.setCount(product.getCount() + 1);
                productRepository.save(product);
                uniq = false;
                break;
            }
        }
        if (uniq) {
            productRepository.save(entity);
        }
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void update(Product entity) {

    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void delete(Long id) {
        Product product = crudRepositoryHelper.findById(productRepository, id).get();
        if (product.getCount() == 1) {
            crudRepositoryHelper.delete(productRepository, id);
        } else {
            product.setCount(product.getCount() - 1);
            productRepository.save(product);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        return Optional.empty();
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
