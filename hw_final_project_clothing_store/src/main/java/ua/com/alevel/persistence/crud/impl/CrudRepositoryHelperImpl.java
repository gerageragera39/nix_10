package ua.com.alevel.persistence.crud.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.BaseEntity;
import ua.com.alevel.persistence.repository.BaseRepository;
import ua.com.alevel.persistence.repository.clothes.ClothesRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CrudRepositoryHelperImpl<
        E extends BaseEntity,
        R extends BaseRepository<E>>
        implements CrudRepositoryHelper<E, R> {

    private final ClothesRepository clothesRepository;

    public CrudRepositoryHelperImpl(ClothesRepository clothesRepository) {
        this.clothesRepository = clothesRepository;
    }

    @Override
    public void create(R repository, E entity) {
        repository.save(entity);
    }

    @Override
    public void update(R repository, E entity) {
        checkExist(repository, entity.getId());
        repository.save(entity);
    }

    @Override
    public void delete(R repository, Long id) {
        checkExist(repository, id);
        repository.deleteById(id);
    }

    @Override
    public Optional<E> findById(R repository, Long id) {
        checkExist(repository, id);
        return repository.findById(id);
    }

    @Override
    public DataTableResponse<E> findAll(R repository, DataTableRequest dataTableRequest) {
//        Sort sort = request.getOrder().equals("desc")
//                ? Sort.by(request.getSort()).descending()
//                : Sort.by(request.getSort()).ascending();
//        Page<E> entityPage = repository.findAll(
//                PageRequest.of(request.getPage() - 1, request.getSize(), sort));
//        DataTableResponse<E> dataTableResponse = new DataTableResponse<>();
//        dataTableResponse.setCurrentPage(request.getPage());
//        dataTableResponse.setPageSize(request.getSize());
//        dataTableResponse.setOrder(request.getOrder());
//        dataTableResponse.setSort(request.getSort());
//        dataTableResponse.setItemsSize(entityPage.getTotalElements());
//        dataTableResponse.setTotalPages(entityPage.getTotalPages());
//        dataTableResponse.setItems(entityPage.getContent());
//        return dataTableResponse;
        Map<Object, Object> otherParamMap = new HashMap<>();
        int page = dataTableRequest.getPage() - 1;
        int size = dataTableRequest.getSize();
        String sortBy = dataTableRequest.getSort();
        String orderBy = dataTableRequest.getOrder();

        List<E> items;
//        if (sortBy.equals("countryCount") || sortBy.equals("personCount")) {
//            PageRequest pageRequest = PageRequest.of(page, size);
//            if (orderBy.equals("desc")) {
//                if (entityClass.isAssignableFrom(Countries.class)) {
//                    items = (List<E>) countriesRepository.findAllByVisibleTrueOrderByPeopleSizeDesc(visible, pageRequest);
//                } else {
//                    items = (List<E>) populationRepository.findAllByVisibleTrueOrderByCountriesSizeDesc(visible, pageRequest);
//                }
//            } else {
//                if (entityClass.isAssignableFrom(Countries.class)) {
//                    items = (List<E>) countriesRepository.findAllByVisibleTrueOrderByPeopleSizeAsc(visible, pageRequest);
//                } else {
//                    items = (List<E>) populationRepository.findAllByVisibleTrueOrderByCountriesSizeAsc(visible, pageRequest);
//                }
//            }
//        } else {
            Sort sort = orderBy.equals("desc")
                    ? Sort.by(sortBy).descending()
                    : Sort.by(sortBy).ascending();
            PageRequest pageRequest = PageRequest.of(page, size, sort);

//            if (entityClass.isAssignableFrom(Countries.class)) {
//                items = (List<E>) countriesRepository.findAllByVisible(visible, pageRequest);
//
//            } else {
//                items = (List<E>) populationRepository.findAllByVisible(visible, pageRequest);
//            }
//        items = (List<E>) clothesRepository.findAllByVisible(true, pageRequest);
        items = repository.findAllByVisibleTrue(pageRequest);
//        }

        DataTableResponse<E> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(items);
        dataTableResponse.setOrder(orderBy);
        dataTableResponse.setSort(sortBy);
        dataTableResponse.setCurrentPage(dataTableRequest.getPage());
//        dataTableResponse.setItemsSize(dataTableRequest.getSize());
        dataTableResponse.setItemsSize(repository.countAllByVisibleTrue());


//        for (int i = 0; i < items.size(); i++) {
//            int numOfCountries;
//            if (items.get(i).getClass().isAssignableFrom(Countries.class)) {
//                dataTableResponse.setItemsSize(countriesRepository.numOfAllCountries(visible));
//                numOfCountries = countriesRepository.findCount(items.get(i).getId());
//            } else {
//                dataTableResponse.setItemsSize(populationRepository.numOfAllPeople(visible));
//                numOfCountries = populationRepository.findCount(items.get(i).getId());
//            }
//            otherParamMap.put(items.get(i).getId(), numOfCountries);
//        }

//        dataTableResponse.setOtherParamMap(otherParamMap);
        return dataTableResponse;
    }

    private void checkExist(R repository, Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("this entity is not found");
        }
    }
}
