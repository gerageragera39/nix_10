package ua.com.alevel.service;

import ua.com.alevel.persistence.entity.User;

import java.util.Map;

public interface UserService extends BaseService<User> {

    Map<Long, String> findAccountsByUserId(Long id);

    boolean existByEmail(String email);

    boolean validEmail(String email);

    boolean checkAll(User user);

    void writeOut(Long id);
}
