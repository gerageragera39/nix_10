package ua.com.alevel.facade.auth_registration.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.facade.auth_registration.RegistrationFacade;
import ua.com.alevel.persistence.entity.users.Personal;
import ua.com.alevel.service.personal.PersonalService;
import ua.com.alevel.web.dto.request.register.AuthDto;

@Service
public class RegistrationFacadeImpl implements RegistrationFacade {

    private final PersonalService personalService;

    public RegistrationFacadeImpl(PersonalService personalService) {
        this.personalService = personalService;
    }

    @Override
    public void registration(AuthDto dto) {
        Personal personal = new Personal();
        personal.setEmail(dto.getEmail());
        personal.setPassword(dto.getPassword());
        personal.setBirthDay(dto.getBirthDay());
        personal.setFirstName(dto.getFirstName());
        personal.setLastName(dto.getLastName());
        personalService.create(personal);
    }
}
