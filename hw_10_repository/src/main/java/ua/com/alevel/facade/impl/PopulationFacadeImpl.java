package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.PopulationFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Population;
import ua.com.alevel.service.CountriesService;
import ua.com.alevel.service.PopulationService;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.view.dto.request.PageAndSizeData;
import ua.com.alevel.view.dto.request.PopulationRequestDto;
import ua.com.alevel.view.dto.request.SortData;
import ua.com.alevel.view.dto.response.PageData;
import ua.com.alevel.view.dto.response.PopulationResponseDto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PopulationFacadeImpl implements PopulationFacade {

    private final PopulationService populationService;
    private final CountriesService countriesService;

    public PopulationFacadeImpl(PopulationService populationService, CountriesService countriesService) {
        this.populationService = populationService;
        this.countriesService = countriesService;
    }

    @Override
    public void create(PopulationRequestDto populationRequestDto) {
        Population person = new Population();
        person.setFirstName(populationRequestDto.getFirstName());
        person.setLastName(populationRequestDto.getLastName());
        person.setAge(populationRequestDto.getAge());
        person.setSex(populationRequestDto.getSex());
        person.setPassportID(populationRequestDto.getPassportID());

        populationService.create(person);
    }

    @Override
    public void update(PopulationRequestDto populationRequestDto, Long id) {
        Population person = new Population();
        person.setId(id);
        person.setFirstName(populationRequestDto.getFirstName());
        person.setLastName(populationRequestDto.getLastName());
        person.setAge(populationRequestDto.getAge());
        person.setSex(populationRequestDto.getSex());
        person.setPassportID(populationRequestDto.getPassportID());
        populationService.update(person);
    }

    @Override
    public void delete(Long id) {
        populationService.delete(id);
    }

    @Override
    public PopulationResponseDto findById(Long id) {
        return new PopulationResponseDto(populationService.findById(id).get());
    }

    @Override
    public PageData<PopulationResponseDto> findAll(WebRequest request) {
        PageAndSizeData pageAndSizeData = WebRequestUtil.generatePageAndSizeData(request);
        SortData sortData = WebRequestUtil.generateSortData(request);
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setPageSize(pageAndSizeData.getSize());
        dataTableRequest.setCurrentPage(pageAndSizeData.getPage());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setOrder(sortData.getOrder());

        DataTableResponse<Population> dataTableResponse = populationService.findAll(dataTableRequest);
        List<PopulationResponseDto> people = dataTableResponse.getItems().stream().
                map(PopulationResponseDto::new).
                peek(authorResponseDto -> authorResponseDto.setCountryCount((Integer) dataTableResponse.
                        getOtherParamMap().get(authorResponseDto.getId()))).
                collect(Collectors.toList());

        PageData<PopulationResponseDto> pageData = new PageData<>();
        pageData.setItems(people);
        pageData.setCurrentPage(pageAndSizeData.getPage());
        pageData.setPageSize(pageAndSizeData.getSize());
        pageData.setSort(sortData.getSort());
        pageData.setOrder(sortData.getOrder());
        pageData.setItemsSize(dataTableResponse.getItemsSize());
        pageData.initPaginationState();
        return pageData;
    }

    public void createRelation(PopulationRequestDto populationRequestDto) {
        String countryName = populationRequestDto.getCountryName();
        String personPassportId = populationRequestDto.getPassportID();
//        populationService.createRelation(countryName, personPassportId);
    }

    @Override
    public void addRelation(PopulationRequestDto dto) {
        String countryName = dto.getCountryName();
        String personPassportId = dto.getPassportID();
        populationService.addRelation(countryName, personPassportId);
    }

    @Override
    public void removeRelation(PopulationRequestDto dto) {
        String countryName = dto.getCountryName();
        String personPassportId = dto.getPassportID();
        populationService.removeRelation(countryName, personPassportId);
    }

    @Override
    public PageData<PopulationResponseDto> findAllNotVisible(WebRequest request) {
        PageAndSizeData pageAndSizeData = WebRequestUtil.generatePageAndSizeData(request);
        SortData sortData = WebRequestUtil.generateSortData(request);
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setPageSize(pageAndSizeData.getSize());
        dataTableRequest.setCurrentPage(pageAndSizeData.getPage());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setOrder(sortData.getOrder());

//        DataTableResponse<Population> dataTableResponse = populationService.findAllNotVisible(dataTableRequest);
        DataTableResponse<Population> dataTableResponse = populationService.findAll(dataTableRequest);
        List<PopulationResponseDto> people = dataTableResponse.getItems().stream().
                map(PopulationResponseDto::new).
                peek(authorResponseDto -> authorResponseDto.setCountryCount((Integer) dataTableResponse.
                        getOtherParamMap().get(authorResponseDto.getId()))).
                collect(Collectors.toList());

        PageData<PopulationResponseDto> pageData = new PageData<>();
        pageData.setItems(people);
        pageData.setCurrentPage(pageAndSizeData.getPage());
        pageData.setPageSize(pageAndSizeData.getSize());
        pageData.setSort(sortData.getSort());
        pageData.setOrder(sortData.getOrder());
        pageData.setItemsSize(dataTableResponse.getItemsSize());
        pageData.initPaginationState();
        return pageData;
    }

    @Override
    public Map<Long, String> findCountriesByPersonId(Long id) {
        return populationService.findCountriesByPersonId(id);
    }

    @Override
    public List<String> findNamesByPersonId(Long id) {
        return populationService.findNamesByPersonId(id);
    }

    @Override
    public List<String> notAddedCountryNamesByPersonId(Long id) {
        List<String> allNames = countriesService.findAllCountriesNames();
        List<String> addedNames = findNamesByPersonId(id);
        for (int i = 0; i < allNames.size(); i++) {
            for (int j = 0; j < addedNames.size(); j++) {
                if (allNames.get(i).equals(addedNames.get(j))) {
                    allNames.remove(i);
                    i--;
                    break;
                }
            }
        }
        return allNames;
    }
}


//Countries country = countriesService.findByName(populationRequestDto.getCountryName());
//        Set<Countries> set = new HashSet<>();
//        set.add(country);
//        person.setCountries(set);
