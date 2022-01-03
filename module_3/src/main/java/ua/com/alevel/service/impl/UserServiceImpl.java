package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.dao.UserDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Account;
import ua.com.alevel.persistence.entity.User;
import ua.com.alevel.service.UserService;
import ua.com.alevel.util.WebResponseUtil;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void create(User entity, String tempField) {
        if (!userDao.existByPassportId(entity.getPassportID()) && !userDao.existByEmail(entity.getEmail()) && ageCheck(entity.getAge())) {
            userDao.create(entity, tempField);
        }
    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(Long id) {
        userDao.delete(id);
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public DataTableResponse<User> findAll(DataTableRequest request) {
        DataTableResponse<User> dataTableResponse = userDao.findAll(request);
        long count = userDao.countVisible();
        WebResponseUtil.initDataTableResponse(request, dataTableResponse, count);
        return dataTableResponse;
    }

    @Override
    public Map<Long, String> findAccountsByUserId(Long id) {
        return userDao.findAccountsByUserId(id);
    }

    @Override
    public boolean existByEmail(String email) {
        return userDao.existByEmail(email);
    }

    public boolean ageCheck(int age) {
        if ((age > 0) && (age < 122)) {
            return true;
        }
        return false;
    }
}
