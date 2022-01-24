package ua.com.alevel.facade.users;

import ua.com.alevel.facade.BaseFacade;
import ua.com.alevel.web.dto.request.users.PersonalRequestDto;
import ua.com.alevel.web.dto.response.users.PersonalResponseDto;

public interface PersonalFacade extends BaseFacade<PersonalRequestDto, PersonalResponseDto> {

    void changeEnable(Long id);

    PersonalResponseDto findByEmail(String username);
}
