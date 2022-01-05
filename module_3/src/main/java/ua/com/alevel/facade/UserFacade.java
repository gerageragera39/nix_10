package ua.com.alevel.facade;

import ua.com.alevel.view.dto.request.UserRequestDto;
import ua.com.alevel.view.dto.response.UserResponseDto;

import java.util.Map;

public interface UserFacade extends BaseFacade<UserRequestDto, UserResponseDto> {

    Map<Long, String> findAccountsByUserId(Long id);

    boolean checkAll(UserRequestDto dto);

    void writeOut(Long id);
}
