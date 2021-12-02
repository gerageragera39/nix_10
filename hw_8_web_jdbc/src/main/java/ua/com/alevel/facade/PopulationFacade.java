package ua.com.alevel.facade;

import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.view.dto.request.PopulationRequestDto;
import ua.com.alevel.view.dto.response.PageData;
import ua.com.alevel.view.dto.response.PopulationResponseDto;

import java.util.Map;

public interface PopulationFacade extends BaseFacade<PopulationRequestDto, PopulationResponseDto> {

    Map<Long, String> findByCountryId(Long id);

    void createRelation(PopulationRequestDto dto);

    void addRelation(PopulationRequestDto dto);

    void removeRelation(PopulationRequestDto dto);

    PageData<PopulationResponseDto> findAllNotVisible(WebRequest request);
}
