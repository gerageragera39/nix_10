package ua.com.alevel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.com.alevel.persistence.cardType.CardType;
import ua.com.alevel.persistence.cities.Cities;
import ua.com.alevel.persistence.dao.AccountDao;
import ua.com.alevel.persistence.dao.UserDao;
import ua.com.alevel.persistence.entity.Account;
import ua.com.alevel.persistence.entity.User;

@SpringBootTest
class Module3ApplicationTests {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AccountDao accountDao;

    @Test
    void contextLoads() {
    }

    @Test
    void init() {
        for (int i = 2; i < 23; i++) {
            User user = userDao.findById((long) i);
            Account account = new Account();
            account.setBalance((double) (i * 100));
            account.setCardType(CardType.Default);
            accountDao.create(account, user.getEmail());
        }
    }
}
