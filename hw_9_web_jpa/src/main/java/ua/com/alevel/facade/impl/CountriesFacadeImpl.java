package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.CountriesFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Countries;
import ua.com.alevel.persistence.entity.Population;
import ua.com.alevel.service.CountriesService;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.view.dto.request.CountriesRequestDto;
import ua.com.alevel.view.dto.request.PageAndSizeData;
import ua.com.alevel.view.dto.request.SortData;
import ua.com.alevel.view.dto.response.CountriesResponseDto;
import ua.com.alevel.view.dto.response.PageData;

import javax.persistence.Table;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CountriesFacadeImpl implements CountriesFacade {

    private final CountriesService countiesService;

    public CountriesFacadeImpl(CountriesService countiesService) {
        this.countiesService = countiesService;
    }

    @Override
    public void create(CountriesRequestDto countriesRequestDto) {
        Countries country = new Countries();
        country.setNameOfCountry(countriesRequestDto.getNameOfCountry());
        country.setISO(countriesRequestDto.getISO());
        countiesService.create(country);
    }

    @Override
    public void update(CountriesRequestDto countriesRequestDto, Long id) {
        Countries country = new Countries();
        country.setId(id);
        country.setNameOfCountry(countriesRequestDto.getNameOfCountry());
        country.setISO(countriesRequestDto.getISO());
        countiesService.update(country);
    }

    @Override
    public void delete(Long id) {
        countiesService.delete(id);
    }

    @Override
    public CountriesResponseDto findById(Long id) {
        return new CountriesResponseDto(countiesService.findById(id));
    }

    @Override
    public PageData<CountriesResponseDto> findAll(WebRequest request) {
        PageAndSizeData pageAndSizeData = WebRequestUtil.generatePageAndSizeData(request);
        SortData sortData = WebRequestUtil.generateSortData(request);
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setPageSize(pageAndSizeData.getSize());
        dataTableRequest.setCurrentPage(pageAndSizeData.getPage());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setOrder(sortData.getOrder());

        DataTableResponse<Countries> dataTableResponse = countiesService.findAll(dataTableRequest);
        List<CountriesResponseDto> countries = dataTableResponse.getItems().stream().
                map(CountriesResponseDto::new).
                peek(countryResponseDto -> countryResponseDto.setPeopleCount((Integer) dataTableResponse.
                        getOtherParamMap().get(countryResponseDto.getId()))).
                collect(Collectors.toList());

        PageData<CountriesResponseDto> pageData = new PageData<>();
        pageData.setItems(countries);
        pageData.setCurrentPage(pageAndSizeData.getPage());
        pageData.setPageSize(pageAndSizeData.getSize());
        pageData.setSort(sortData.getSort());
        pageData.setOrder(sortData.getOrder());
        pageData.setItemsSize(dataTableResponse.getItemsSize());
        pageData.initPaginationState();
        return pageData;
    }

    public List<String> findAllCountriesNames() {
        return countiesService.findAllCountriesNames();
    }

//    @Override
//    public List<String> findNamesByPersonId(Long id) {
//        List<Countries> countries = countiesService.findNamesByPersonId(id).stream().toList();
//        List<String> names = new ArrayList<>();
//        for (int i = 0; i < countries.size(); i++) {
//            names.add(countries.get(i).getNameOfCountry());
//        }
//        List<String> simpleNames = new ArrayList<>();
//        String simpleName = " ";
//        boolean firstStep = true;
//        for (int i = 0; i < names.size(); i++) {
//            for (int j = 0; j < names.get(i).length(); j++) {
//                if (names.get(i).charAt(j) != ' ') {
//                    if (firstStep) {
//                        simpleName = String.valueOf(names.get(i).charAt(j));
//                        firstStep = false;
//                    } else {
//                        simpleName += String.valueOf(names.get(i).charAt(j));
//                    }
//                } else {
//                    break;
//                }
//            }
//            firstStep = true;
//            simpleNames.add(simpleName);
//        }
//        return simpleNames;
//    }

//    @Override
//    public List<String> notAddedCountryNamesByPersonId(Long id) {
//        List<String> allNames = countiesService.findAllCountriesNames();
////        List<String> addedNames = findNamesByPersonId(id);
////        for (int i = 0; i < allNames.size(); i++) {
////            for (int j = 0; j < addedNames.size(); j++) {
////                if (allNames.get(i).equals(addedNames.get(j))) {
////                    allNames.remove(i);
////                }
////            }
////        }
//        return allNames;
//    }

    @Override
    public Map<Long, String> findPeopleByCountryId(Long id) {
        return countiesService.findPeopleByCountryId(id);
    }
}
