package ua.com.alevel.service.brand;

import ua.com.alevel.persistence.entity.brands.Brand;
import ua.com.alevel.service.BaseService;

public interface BrandService extends BaseService<Brand> {

    Brand findByName(String brandName);
}
