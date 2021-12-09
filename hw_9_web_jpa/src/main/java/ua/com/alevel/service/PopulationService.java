package ua.com.alevel.service;

import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Population;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PopulationService extends BaseService<Population> {

    Map<Long, String> findCountriesByPersonId(Long id);

    List<String> findNamesByPersonId(Long id);

    List<String> findAllCountriesNames();

    void addRelation(String countryName, String personPassportId);

    void removeRelation(String countryName, String personPassportId);

    DataTableResponse<Population> findAllNotVisible(DataTableRequest dataTableRequest);
}
