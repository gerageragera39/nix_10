package ua.com.alevel.facade;

import ua.com.alevel.view.dto.request.CountriesRequestDto;
import ua.com.alevel.view.dto.response.CountriesResponseDto;

import java.util.List;
import java.util.Map;

public interface CountriesFacade extends BaseFacade<CountriesRequestDto, CountriesResponseDto> {

    List<String> findAllCountriesNames();

    Map<Long, String> findPeopleByCountryId(Long id);
}
