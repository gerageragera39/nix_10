package ua.com.alevel.service;

import ua.com.alevel.persistence.entity.User;
import ua.com.alevel.view.dto.request.UserRequestDto;

import java.util.Map;

public interface UserService extends BaseService<User> {

    Map<Long, String> findAccountsByUserId(Long id);
}
