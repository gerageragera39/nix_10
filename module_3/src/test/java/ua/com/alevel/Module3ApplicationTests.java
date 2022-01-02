package ua.com.alevel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.com.alevel.persistence.cities.Cities;
import ua.com.alevel.persistence.dao.UserDao;
import ua.com.alevel.persistence.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
class Module3ApplicationTests {

    @Autowired
    private UserDao userDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void contextLoads() {
    }

    @Test
    void create(){
        for (int i = 0; i < 10; i++) {
            User user = new User();
//            user.setId(Long.getLong(String.valueOf(i)));
            user.setFirstName("fn" + i);
            user.setLastName("ln" + i);
            user.setAge(i);
            user.setEmail("email" + i);
            user.setCity(Cities.Kharkov);
            user.setPassportID("pid" + i);
            userDao.create(user, user.getEmail());
        }

//        User user = new User();
////            user.setId(Long.getLong(String.valueOf(i)));
//        user.setFirstName("fn");
//        user.setLastName("ln");
//        user.setAge(13);
//        user.setEmail("email");
//        user.setCity(Cities.Kharkov);
//        user.setPassportID("pid");
//        userDao.create(user);
    }

}
