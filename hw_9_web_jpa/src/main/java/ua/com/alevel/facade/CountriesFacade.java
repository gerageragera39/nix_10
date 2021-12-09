package ua.com.alevel.facade;

import ua.com.alevel.persistence.entity.Countries;
import ua.com.alevel.persistence.entity.Population;
import ua.com.alevel.view.dto.request.CountriesRequestDto;
import ua.com.alevel.view.dto.response.CountriesResponseDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CountriesFacade extends BaseFacade<CountriesRequestDto, CountriesResponseDto> {

    List<String> findAllCountriesNames();

    Map<Long, String> findPeopleByCountryId(Long id);
}
