package ua.com.alevel.service;

import ua.com.alevel.persistence.entity.Population;

import java.util.List;
import java.util.Map;

public interface PopulationService extends BaseService<Population> {

    Map<Long, String> findCountriesByPersonId(Long id);

    List<String> findNamesByPersonId(Long id);

    void addRelation(String countryName, String personPassportId);

    void removeRelation(String countryName, String personPassportId);
}
