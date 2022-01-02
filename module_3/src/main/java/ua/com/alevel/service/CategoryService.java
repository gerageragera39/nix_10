package ua.com.alevel.service;

import ua.com.alevel.persistence.entity.Category;

import java.util.List;

public interface CategoryService extends BaseService<Category> {

    List<String> getAllCategoryNames();

    Category findByName(String categoryName);
}
