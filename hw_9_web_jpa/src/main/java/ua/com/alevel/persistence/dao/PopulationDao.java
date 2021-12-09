package ua.com.alevel.persistence.dao;

import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Population;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PopulationDao extends BaseDao<Population> {

    int countNumOfCountries(Long id);

    Map<Long, String> findCountriesByPersonId(Long id);

    List<String> findNamesByPersonId(Long id);

    List<String> findAllCountriesNames();

    void addRelation(String countryName, String personPassportId);

    void removeRelation(String countryName, String personPassportId);

    DataTableResponse<Population> findAllNotVisible(DataTableRequest dataTableRequest);

    long countNotVisible();
}
