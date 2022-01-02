package ua.com.alevel.persistence.dao;

import ua.com.alevel.persistence.entity.Category;

import java.util.List;

public interface CategoryDao extends BaseDao<Category>{

    List<String> getAllCategoryNames();

    Category findByName(String categoryName);
}
