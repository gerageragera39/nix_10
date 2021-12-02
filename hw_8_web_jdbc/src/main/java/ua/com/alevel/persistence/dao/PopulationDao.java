package ua.com.alevel.persistence.dao;

import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Population;

import java.util.Map;

public interface PopulationDao extends BaseDao<Population>{

    Map<Long, String> findByCountryId(Long id);

    void createRelation(String countryName, String personPassportId);

    void addRelation(String countryName, String personPassportId);

    boolean existById(String passportId);

    void removeRelation(String countryName, String personPassportId);

    DataTableResponse<Population> findAllNotVisible(DataTableRequest dataTableRequest);
}
