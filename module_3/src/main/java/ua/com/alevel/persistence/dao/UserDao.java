package ua.com.alevel.persistence.dao;

import ua.com.alevel.persistence.entity.User;
import ua.com.alevel.view.dto.request.UserRequestDto;

import java.util.Map;

public interface UserDao extends BaseDao<User>{

    Map<Long, String> findAccountsByUserId(Long id);

    boolean existByPassportId(String passportID);

    boolean existByEmail(String email);
}
