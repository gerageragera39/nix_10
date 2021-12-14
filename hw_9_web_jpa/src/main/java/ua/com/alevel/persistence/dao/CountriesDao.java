package ua.com.alevel.persistence.dao;

import ua.com.alevel.persistence.entity.Countries;
import ua.com.alevel.persistence.entity.Population;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CountriesDao extends BaseDao<Countries> {

    List<String> findAllCountriesNames();

    int countNumOfPeople(Long id);

    Map<Long, String> findPeopleByCountryId(Long id);

    Countries findByName(String countryName);

    boolean existByISOAndCountyName(String nameOfCountry, Integer iso);
}
