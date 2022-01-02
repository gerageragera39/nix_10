package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.dao.CategoryDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Category;
import ua.com.alevel.service.CategoryService;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;

    public CategoryServiceImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public void create(Category entity, String tempField) {
        categoryDao.create(entity,tempField);
    }

    @Override
    public void update(Category entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Category findById(Long id) {
        return null;
    }

    @Override
    public DataTableResponse<Category> findAll(DataTableRequest request) {
        return categoryDao.findAll(request);
    }

    @Override
    public List<String> getAllCategoryNames() {
        return categoryDao.getAllCategoryNames();
    }

    @Override
    public Category findByName(String categoryName) {
        return categoryDao.findByName(categoryName);
    }
}
