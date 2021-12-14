package ua.com.alevel.facade;

import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.view.dto.request.PopulationRequestDto;
import ua.com.alevel.view.dto.response.PageData;
import ua.com.alevel.view.dto.response.PopulationResponseDto;

import java.util.List;
import java.util.Map;

public interface PopulationFacade extends BaseFacade<PopulationRequestDto, PopulationResponseDto> {

    void createRelation(PopulationRequestDto dto);

    void addRelation(PopulationRequestDto dto);

    void removeRelation(PopulationRequestDto dto);

    PageData<PopulationResponseDto> findAllNotVisible(WebRequest request);

    Map<Long, String> findCountriesByPersonId(Long id);

    List<String> findNamesByPersonId(Long id);

    List<String> notAddedCountryNamesByPersonId(Long id);
}
