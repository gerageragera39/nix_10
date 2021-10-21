package ua.com.alevel.service;

import ua.com.alevel.dao.UserDao;

import ua.com.alevel.entity.User;

public class UserService {

    private final UserDao userDao = new UserDao();

    public int create(User user, User[] users, int index) {

        if(index!=0){
            if (!userDao.existByEmail(user.getEmail())) {
                userDao.create(user);
            } else {
                System.out.println();
                System.out.println("USER DOES NOT EXIST AT THIS EMAIL");
            }
        }
        if(index == 0){
            userDao.create(user);
            index = 1;
        }
        return index;
    }

    public void update(User user) {
        userDao.update(user);
    }

    public void delete(String id) {
        userDao.delete(id);
    }

    public User findById(String id) {
        return userDao.findById(id);
    }

    public User[] findAll() {
        return userDao.findAll();
    }
}
