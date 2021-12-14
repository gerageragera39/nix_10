package ua.com.alevel.service;

import ua.com.alevel.persistence.entity.Countries;

import java.util.List;
import java.util.Map;

public interface CountriesService extends BaseService<Countries> {

    List<String> findAllCountriesNames();

    Map<Long, String> findPeopleByCountryId(Long id);

    Countries findByName(String countryName);
}
