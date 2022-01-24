package ua.com.alevel.service.personal;

import ua.com.alevel.persistence.entity.users.Personal;
import ua.com.alevel.service.BaseService;

public interface PersonalService extends BaseService<Personal> {

    Personal findByEmail(String personalEmail);
}
