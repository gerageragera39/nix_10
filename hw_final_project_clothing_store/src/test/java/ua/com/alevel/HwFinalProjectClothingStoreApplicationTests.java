package ua.com.alevel;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ua.com.alevel.persistence.entity.brands.Brand;
import ua.com.alevel.service.brand.BrandService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClothingStoreApplication.class)
@TestPropertySource("/application.properties")
class HwFinalProjectClothingStoreApplicationTests {

    @Test
    public void test() {
    }
}
