package ua.com.alevel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.com.alevel.persistence.entity.Population;
import ua.com.alevel.persistence.repository.CountriesRepository;
import ua.com.alevel.persistence.repository.PopulationRepository;
import ua.com.alevel.service.PopulationService;

import java.util.Random;

@SpringBootTest
class Hw10RepositoryApplicationTests {

    @Autowired
    private PopulationRepository populationRepository;

    @Autowired
    private CountriesRepository countriesRepository;

    @Autowired
    private PopulationService populationService;

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
                populationService.addRelation(countriesRepository.findById((long) i).get().getNameOfCountry(), populationRepository.findById(randomPersonId).get().getPassportID());
            }
        }

        for (int i = 1; i <= 22; i++) {
            Population person = populationRepository.findById((long) i).get();
            if(person.getCountries().size() != 0){
                person.setVisible(true);
                populationService.update(person);
            }
        }
    }
}
