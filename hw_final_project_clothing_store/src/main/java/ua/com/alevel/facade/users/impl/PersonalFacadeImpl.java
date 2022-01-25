package ua.com.alevel.facade.users.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.users.PersonalFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.users.Personal;
import ua.com.alevel.service.personal.PersonalService;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.web.dto.request.PageAndSizeData;
import ua.com.alevel.web.dto.request.SortData;
import ua.com.alevel.web.dto.request.users.PersonalRequestDto;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.users.PersonalResponseDto;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonalFacadeImpl implements PersonalFacade {

    private final PersonalService personalService;

    public PersonalFacadeImpl(PersonalService personalService) {
        this.personalService = personalService;
    }

    @Override
    public void create(PersonalRequestDto personalRequestDto) {

    }

    @Override
    public void update(PersonalRequestDto dto, Long id) {
        Optional<Personal> optionalPersonal = personalService.findById(id);
        if (optionalPersonal.isPresent()) {
            Personal personal = optionalPersonal.get();
            if (StringUtils.isNotBlank(dto.getFirstName())) {
                personal.setFirstName(dto.getFirstName());
            }
            if (StringUtils.isNotBlank(dto.getLastName())) {
                personal.setLastName(dto.getLastName());
            }
            if (StringUtils.isNotBlank(dto.getPassword())) {
                personal.setPassword(dto.getPassword());
            }
            personal.setUpdated(new Date());
            personalService.update(personal);
        }
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public PersonalResponseDto findById(Long id) {
        return new PersonalResponseDto(personalService.findById(id).get());
    }

    @Override
    public PageData<PersonalResponseDto> findAll(WebRequest request) {
        PageAndSizeData pageAndSizeData = WebRequestUtil.generatePageAndSizeData(request);
        SortData sortData = WebRequestUtil.generateSortData(request);
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setSize(pageAndSizeData.getSize());
        dataTableRequest.setPage(pageAndSizeData.getPage());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setOrder(sortData.getOrder());

        DataTableResponse<Personal> dataTableResponse = personalService.findAll(dataTableRequest);
        List<PersonalResponseDto> brands = dataTableResponse.getItems().stream().
                map(PersonalResponseDto::new).
                collect(Collectors.toList());

        PageData<PersonalResponseDto> pageData = new PageData<>();
        pageData.setItems(brands);
        pageData.setCurrentPage(pageAndSizeData.getPage());
        pageData.setPageSize(pageAndSizeData.getSize());
        pageData.setSort(sortData.getSort());
        pageData.setOrder(sortData.getOrder());
        pageData.setItemsSize(dataTableResponse.getItemsSize());
        pageData.initPaginationState();
        return pageData;
    }

    @Override
    public void changeEnable(Long id) {
        Optional<Personal> optionalPersonal = personalService.findById(id);
        if (optionalPersonal.isPresent()) {
            Personal personal = optionalPersonal.get();
            personal.setEnabled(!personal.getEnabled());
            personal.setUpdated(new Date());
            personalService.update(personal);
        }
    }

    @Override
    public PersonalResponseDto findByEmail(String username) {
        return new PersonalResponseDto(personalService.findByEmail(username));
    }
}
