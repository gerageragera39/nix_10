package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.CountriesFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Countries;
import ua.com.alevel.service.CountriesService;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.view.dto.request.CountriesRequestDto;
import ua.com.alevel.view.dto.request.PageAndSizeData;
import ua.com.alevel.view.dto.request.SortData;
import ua.com.alevel.view.dto.response.CountriesResponseDto;
import ua.com.alevel.view.dto.response.PageData;

import java.util.List;
import java.util.Map;
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

    @Override
    public Map<Long, String> findPeopleByCountryId(Long id) {
        return countiesService.findPeopleByCountryId(id);
    }
}
