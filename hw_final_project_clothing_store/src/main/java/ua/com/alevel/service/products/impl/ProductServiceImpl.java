package ua.com.alevel.service.products.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.colors.Color;
import ua.com.alevel.persistence.entity.products.Product;
import ua.com.alevel.persistence.entity.users.Personal;
import ua.com.alevel.persistence.repository.colors.ColorRepository;
import ua.com.alevel.persistence.repository.products.ProductRepository;
import ua.com.alevel.persistence.repository.sizes.SizeRepository;
import ua.com.alevel.service.personal.PersonalService;
import ua.com.alevel.service.products.ProductService;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CrudRepositoryHelper<Product, ProductRepository> crudRepositoryHelper;

    public ProductServiceImpl(ProductRepository productRepository, CrudRepositoryHelper<Product, ProductRepository> crudRepositoryHelper, ColorRepository colorRepository, SizeRepository sizeRepository) {
        this.productRepository = productRepository;
        this.crudRepositoryHelper = crudRepositoryHelper;
    }

    @Override
    public void create(Product entity) {
//        crudRepositoryHelper.create(productRepository, entity);
        productRepository.save(entity);
    }

    @Override
    public void update(Product entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public DataTableResponse<Product> findAll(DataTableRequest request) {
        return null;
    }

    @Override
    public List<Product> findByPersonalEmail(Personal personal) {
        return productRepository.findAllByPersonal(personal);
    }
}
