package ua.com.alevel.service;

import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Population;

import java.util.Map;

public interface PopulationService extends BaseService<Population> {

    Map<Long, String> findByCountryId(Long id);

    void createRelation(String countryName, String personPassportId);

    void addRelation(String countryName, String personPassportId);

    void removeRelation(String countryName, String personPassportId);

    DataTableResponse<Population> findAllNotVisible(DataTableRequest dataTableRequest);
}
