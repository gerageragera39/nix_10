package ua.com.alevel.persistence.crud.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.BaseEntity;
import ua.com.alevel.persistence.entity.Countries;
import ua.com.alevel.persistence.entity.Population;
import ua.com.alevel.persistence.repository.BaseRepository;
import ua.com.alevel.persistence.repository.CountriesRepository;
import ua.com.alevel.persistence.repository.PopulationRepository;

import java.util.*;

@Service
public class CrudRepositoryHelperImpl<E extends BaseEntity, R extends BaseRepository<E>>
        implements CrudRepositoryHelper<E, R> {

    private final PopulationRepository populationRepository;
    private final CountriesRepository countriesRepository;

    public CrudRepositoryHelperImpl(PopulationRepository populationRepository, CountriesRepository countriesRepository) {
        this.populationRepository = populationRepository;
        this.countriesRepository = countriesRepository;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void create(R repository, E entity) {
        repository.save(entity);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void update(R repository, E entity) {
        checkById(repository, entity.getId());
        repository.save(entity);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void delete(R repository, Long id, Class entityClass) {
        if (entityClass.isAssignableFrom(Countries.class)) {
            Countries country = countriesRepository.findById(id).get();
            List<Population> people = country.getPeople().stream().toList();
            for (int i = 0; i < people.size(); i++) {
                country.removePerson(people.get(i));
            }

            for (int i = 0; i < people.size(); i++) {
                if (people.get(i).getCountries().size() == 0) {
                    people.get(i).setVisible(false);
                    update((R) populationRepository, (E) people.get(i));
                }
            }
            update((R) countriesRepository, (E) country);
        } else {
            Population person = populationRepository.findById(id).get();
            List<Countries> countries = person.getCountries().stream().toList();
            for (int i = 0; i < countries.size(); i++) {
                countries.get(i).removePerson(person);
                update((R) countriesRepository, (E) countries.get(i));
            }
        }
        checkById(repository, id);
        repository.deleteById(id);

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<E> findById(R repository, Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public DataTableResponse<E> findAll(R repository, DataTableRequest dataTableRequest, Class entityClass, boolean visible) {
        Map<Object, Object> otherParamMap = new HashMap<>();
        int page = dataTableRequest.getCurrentPage() - 1;
        int size = dataTableRequest.getPageSize();
        String sortBy = dataTableRequest.getSort();
        String orderBy = dataTableRequest.getOrder();

        List<E> items;
        if (sortBy.equals("countryCount") || sortBy.equals("personCount")) {
            PageRequest pageRequest = PageRequest.of(page, size);
            if (orderBy.equals("desc")) {
                if (entityClass.isAssignableFrom(Countries.class)) {
                    items = (List<E>) countriesRepository.findAllByVisibleTrueOrderByPeopleSizeDesc(visible, pageRequest);
                } else {
                    items = (List<E>) populationRepository.findAllByVisibleTrueOrderByCountriesSizeDesc(visible, pageRequest);
                }
            } else {
                if (entityClass.isAssignableFrom(Countries.class)) {
                    items = (List<E>) countriesRepository.findAllByVisibleTrueOrderByPeopleSizeAsc(visible, pageRequest);
                } else {
                    items = (List<E>) populationRepository.findAllByVisibleTrueOrderByCountriesSizeAsc(visible, pageRequest);
                }
            }
        } else {
            Sort sort = orderBy.equals("desc")
                    ? Sort.by(sortBy).descending()
                    : Sort.by(sortBy).ascending();
            PageRequest pageRequest = PageRequest.of(page, size, sort);

            if (entityClass.isAssignableFrom(Countries.class)) {
                items = (List<E>) countriesRepository.findAllByVisible(visible, pageRequest);

            } else {
                items = (List<E>) populationRepository.findAllByVisible(visible, pageRequest);
            }
        }

        DataTableResponse<E> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(items);
        dataTableResponse.setOrder(orderBy);
        dataTableResponse.setSort(sortBy);
        dataTableResponse.setCurrentPage(dataTableRequest.getCurrentPage());
        dataTableResponse.setCurrentSize(dataTableRequest.getPageSize());

        for (int i = 0; i < items.size(); i++) {
            int numOfCountries;
            if (items.get(i).getClass().isAssignableFrom(Countries.class)) {
                dataTableResponse.setItemsSize(countriesRepository.numOfAllCountries(visible));
                numOfCountries = countriesRepository.findCount(items.get(i).getId());
            } else {
                dataTableResponse.setItemsSize(populationRepository.numOfAllPeople(visible));
                numOfCountries = populationRepository.findCount(items.get(i).getId());
            }
            otherParamMap.put(items.get(i).getId(), numOfCountries);
        }
        dataTableResponse.setOtherParamMap(otherParamMap);
        return dataTableResponse;
    }

    @Override
    public void addRelation(String countryName, String personPassportId) {
        Countries country = countriesRepository.findByNameOfCountry(countryName);
        Population person = populationRepository.findByPassportID(personPassportId);
        country.addPerson(person);
        update((R) countriesRepository, (E) country);
    }

    @Override
    public void removeRelation(String countryName, String personPassportId) {
        Countries country = countriesRepository.findByNameOfCountry(countryName);
        Population person = populationRepository.findByPassportID(personPassportId);
        country.removePerson(person);
        update((R) populationRepository, (E) person);
        update((R) countriesRepository, (E) country);
    }

    private void checkById(R repository, Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("entity not found");
        }
    }
}
