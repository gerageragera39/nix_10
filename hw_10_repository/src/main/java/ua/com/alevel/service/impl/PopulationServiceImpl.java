package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Population;
import ua.com.alevel.persistence.repository.PopulationRepository;
import ua.com.alevel.service.PopulationService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PopulationServiceImpl implements PopulationService {

    private final CrudRepositoryHelper<Population, PopulationRepository> crudRepositoryHelper;
    private final PopulationRepository populationRepository;

    public PopulationServiceImpl(CrudRepositoryHelper<Population, PopulationRepository> crudRepositoryHelper, PopulationRepository employeeRepository) {
        this.crudRepositoryHelper = crudRepositoryHelper;
        this.populationRepository = employeeRepository;
    }

    @Override
    public void create(Population entity) {
        crudRepositoryHelper.create(populationRepository, entity);
    }

    @Override
    public void update(Population entity) {
        crudRepositoryHelper.update(populationRepository, entity);
    }

    @Override
    public void delete(Long id) {
        crudRepositoryHelper.delete(populationRepository, id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Population> findById(Long id) {
        return crudRepositoryHelper.findById(populationRepository, id);
    }

    @Override
    public DataTableResponse<Population> findAll(DataTableRequest request) {
        return crudRepositoryHelper.findAll(populationRepository, request);
    }

    @Override
    public Map<Long, String> findCountriesByPersonId(Long id) {
        return null;
    }

    @Override
    public List<String> findNamesByPersonId(Long id) {
        return null;
    }

    @Override
    public List<String> findAllCountriesNames() {
        return null;
    }

    @Override
    public void addRelation(String countryName, String personPassportId) {

    }

    @Override
    public void removeRelation(String countryName, String personPassportId) {

    }

    @Override
    public DataTableResponse<Population> findAllNotVisible(DataTableRequest dataTableRequest) {
        return null;
    }
}
