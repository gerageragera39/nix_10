package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.UserFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.User;
import ua.com.alevel.service.UserService;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.view.dto.request.PageAndSizeData;
import ua.com.alevel.view.dto.request.SortData;
import ua.com.alevel.view.dto.request.UserRequestDto;
import ua.com.alevel.view.dto.response.PageData;
import ua.com.alevel.view.dto.response.UserResponseDto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;

    public UserFacadeImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void create(UserRequestDto userRequestDto, String tempField) {
        User user = new User();
        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        user.setAge(userRequestDto.getAge());
        user.setEmail(userRequestDto.getEmail());
        user.setCity(userRequestDto.getCity());
        user.setPassportID(userRequestDto.getPassportID());
        userService.create(user, tempField);
    }

    @Override
    public void update(UserRequestDto userRequestDto, Long id) {
        User user = userService.findById(id);
        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        user.setAge(userRequestDto.getAge());
        user.setEmail(userRequestDto.getEmail());
        user.setCity(userRequestDto.getCity());
        userService.update(user);
    }

    @Override
    public void delete(Long id) {
        userService.delete(id);
    }

    @Override
    public UserResponseDto findById(Long id) {
        return new UserResponseDto(userService.findById(id));
    }

    @Override
    public PageData<UserResponseDto> findAll(WebRequest request) {
        PageAndSizeData pageAndSizeData = WebRequestUtil.generatePageAndSizeData(request);
        SortData sortData = WebRequestUtil.generateSortData(request);
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setPageSize(pageAndSizeData.getSize());
        dataTableRequest.setCurrentPage(pageAndSizeData.getPage());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setOrder(sortData.getOrder());

        DataTableResponse<User> dataTableResponse = userService.findAll(dataTableRequest);
        List<UserResponseDto> users = dataTableResponse.getItems().stream().
                map(UserResponseDto::new).
                peek(userResponseDto -> userResponseDto.setAccountCount((Integer) dataTableResponse.
                        getOtherParamMap().get(userResponseDto.getId()))).
                collect(Collectors.toList());

        PageData<UserResponseDto> pageData = new PageData<>();
        pageData.setItems(users);
        pageData.setCurrentPage(pageAndSizeData.getPage());
        pageData.setPageSize(pageAndSizeData.getSize());
        pageData.setSort(sortData.getSort());
        pageData.setOrder(sortData.getOrder());
        pageData.setItemsSize(dataTableResponse.getItemsSize());
        pageData.initPaginationState();
        return pageData;
    }

    @Override
    public Map<Long, String> findAccountsByUserId(Long id) {
        return userService.findAccountsByUserId(id);
    }

    @Override
    public boolean checkAll(UserRequestDto dto) {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setAge(dto.getAge());
        user.setPassportID(dto.getPassportID());
        user.setEmail(dto.getEmail());
        user.setCity(dto.getCity());
        return userService.checkAll(user);
    }

    @Override
    public void writeOut(Long id) {
        userService.writeOut(id);
    }
}
