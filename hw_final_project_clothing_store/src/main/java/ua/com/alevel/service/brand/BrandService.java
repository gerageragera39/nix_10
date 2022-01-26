package ua.com.alevel.service.brand;

import ua.com.alevel.persistence.entity.brands.Brand;
import ua.com.alevel.service.BaseService;

import javax.crypto.spec.OAEPParameterSpec;
import java.util.Map;
import java.util.Optional;

public interface BrandService extends BaseService<Brand> {

    Optional<Brand> findByName(String brandName);

    Map<Long, String> findAll();

    boolean existByName(String name);
}
