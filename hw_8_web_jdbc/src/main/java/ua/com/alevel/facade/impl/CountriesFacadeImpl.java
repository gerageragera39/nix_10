package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.CountriesFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Countries;
import ua.com.alevel.service.CountiesService;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.view.dto.request.CountriesRequestDto;
import ua.com.alevel.view.dto.request.PageAndSizeData;
import ua.com.alevel.view.dto.request.SortData;
import ua.com.alevel.view.dto.response.CountriesResponseDto;
import ua.com.alevel.view.dto.response.PageData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CountriesFacadeImpl implements CountriesFacade {

    private final CountiesService countiesService;

    public CountriesFacadeImpl(CountiesService countiesService) {
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
//        PageAndSizeData pageAndSizeData = WebRequestUtil.generatePageAndSizeData(request);
//        SortData sortData = WebRequestUtil.generateSortData(request);
//
//        DataTableRequest dataTableRequest = new DataTableRequest();
//        dataTableRequest.setOrder(sortData.getOrder());
//        dataTableRequest.setSort(sortData.getSort());
//        dataTableRequest.setCurrentPage(pageAndSizeData.getPage());
//        dataTableRequest.setPageSize(dataTableRequest.getPageSize());
//
//        DataTableResponse<Countries> dataTableResponse = countiesService.findAll(dataTableRequest);
//
//        PageData<CountriesResponseDto> pageData = new PageData<>();
//        List<CountriesResponseDto> items = dataTableResponse.getItems().stream().map(CountriesResponseDto::new).collect(Collectors.toList());
//        pageData.setItems(items);
//
//        return pageData;
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

    public List<String> findAllCountriesNames(){
        return countiesService.findAllCountriesNames();
    }

    @Override
    public Map<Long, String> findByPersonId(Long id) {
        return countiesService.findByCountryId(id);
    }

    @Override
    public List<String> findNamesByPersonId(Long id) {
        List<String> names = countiesService.findByCountryId(id).values().stream().toList();
        List<String> simpleNames = new ArrayList<>();
        String simpleName = " ";
        boolean firstStep = true;
        for (int i = 0; i < names.size(); i++) {
            for (int j = 0; j < names.get(i).length(); j++) {
                if(names.get(i).charAt(j) != ' '){
                    if(firstStep){
                        simpleName = String.valueOf(names.get(i).charAt(j));
                        firstStep = false;
                    }else{
                        simpleName += String.valueOf(names.get(i).charAt(j));
                    }
                } else {
                    break;
                }
            }
            firstStep = true;
            simpleNames.add(simpleName);
        }
        return simpleNames;
    }

    @Override
    public List<String> notAddedCountryNamesByPersonId(Long id) {
        List<String> allNames = countiesService.findAllCountriesNames();
        List<String> addedNames = findNamesByPersonId(id);
        for (int i = 0; i < allNames.size(); i++) {
            for (int j = 0; j < addedNames.size(); j++) {
                if(allNames.get(i).equals(addedNames.get(j))){
                    allNames.remove(i);
                }
            }
        }
        return allNames;
    }
}
