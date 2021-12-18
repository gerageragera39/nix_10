package ua.com.alevel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.com.alevel.persistence.dao.CountriesDao;
import ua.com.alevel.persistence.dao.PopulationDao;
import ua.com.alevel.persistence.entity.Population;

import java.util.Random;

@SpringBootTest
class Hw9WebJpaApplicationTests {

    @Autowired
    private PopulationDao populationDao;

    @Autowired
    private CountriesDao countriesDao;

    @Test
    void contextLoads() {
    }

    @Test
    void initRelations() {
        Random random = new Random();
        for (int i = 1; i < 21; i++) {
            int numOfRelations = random.nextInt(0, 10);
            for (int j = 0; j < numOfRelations; j++) {
                long randomPersonId = random.nextLong(1,22);
                populationDao.addRelation(countriesDao.findById((long) i).getNameOfCountry(), populationDao.findById(randomPersonId).getPassportID());
            }
        }

        for (int i = 1; i <= 22; i++) {
            Population person = populationDao.findById((long) i);
            if(person.getCountries().size() != 0){
                person.setVisible(true);
                populationDao.update(person);
            }
        }
    }
}
