package ua.com.alevel.service;

import ua.com.alevel.persistence.entity.Countries;

import java.util.List;
import java.util.Map;

public interface CountiesService extends BaseService<Countries> {

    List<String> findAllCountriesNames();

    Map<Long, String> findByCountryId(Long id);
}
