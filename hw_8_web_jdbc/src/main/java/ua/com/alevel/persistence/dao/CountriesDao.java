package ua.com.alevel.persistence.dao;

import ua.com.alevel.persistence.entity.Countries;

import java.util.List;
import java.util.Map;

public interface CountriesDao extends BaseDao<Countries>{
    List<String> findAllCountriesNames();

    Map<Long, String> findByCountryId(Long id);

    boolean existById(String countryName, int ISO);
}
