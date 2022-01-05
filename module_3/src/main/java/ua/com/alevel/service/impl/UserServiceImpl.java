package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.dao.UserDao;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.User;
import ua.com.alevel.service.UserService;
import ua.com.alevel.util.WebResponseUtil;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    public static final Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void create(User entity, String tempField) {
        if (checkAll(entity)) {
            userDao.create(entity, tempField);
        }
    }

    @Override
    public void update(User entity) {
        if (userDao.existByPassportId(entity.getPassportID()) && !userDao.existByEmail(entity.getEmail()) && ageAndEmailCheck(entity.getAge()) && validEmail(entity.getEmail())) {
            userDao.update(entity);
        }
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

    @Override
    public boolean validEmail(String email) {
        Matcher matcher = emailPattern.matcher(email);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkAll(User user) {
        return !userDao.existByPassportId(user.getPassportID()) && !userDao.existByEmail(user.getEmail()) && ageAndEmailCheck(user.getAge()) && validEmail(user.getEmail());
    }

    @Override
    public void writeOut(Long id) {
        userDao.writeOut(id);
    }

    public boolean ageAndEmailCheck(int age) {
        if ((age > 0) && (age < 122)) {
            return true;
        }
        return false;
    }
}
