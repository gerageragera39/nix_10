package ua.com.alevel;

import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ua.com.alevel.persistence.entity.brands.Brand;
import ua.com.alevel.service.brand.BrandService;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

//@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClothingStoreApplication.class)
@TestPropertySource("/application.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BrandServiceTest {

    public static final String NAME_OF_BRAND = "testBrandName";
    private static final String NAME_OF_BRAND_UPDATE = "testBrandName_update";
    private static final int DEFAULT_SIZE = 10;

    @Autowired
    private BrandService brandService;

    @Test
    @Order(1)
    public void setUp() {
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            Brand brand = generateBrand(NAME_OF_BRAND + i);
            brandService.create(brand);
        }
        Assertions.assertEquals(brandService.findAll().size(), DEFAULT_SIZE);
    }

    @Test
    @Order(2)
    public void shouldBeCreatedWhenNameIsNotDuplicate() {
        Brand brand = generateBrand(NAME_OF_BRAND + (DEFAULT_SIZE + 1));
        brandService.create(brand);
        Assertions.assertEquals(brandService.findAll().size(), DEFAULT_SIZE + 1);
    }

    @Test
    @Order(3)
    public void shouldBeCreatedWhenNameIsDuplicate() {
        Brand brand = generateBrand(NAME_OF_BRAND + 1);
        brandService.create(brand);
        Assertions.assertEquals(brandService.findAll().size(), DEFAULT_SIZE + 1);
    }

    @Test
    @Order(4)
    public void shouldBeUpdatedById() {
        Random random = new Random();
        int id = random.nextInt(1, DEFAULT_SIZE + 1);
        Brand brand = brandService.findById((long) id).get();
        brand.setName(NAME_OF_BRAND_UPDATE);
        brandService.update(brand);
        Brand updatedBrand = brandService.findById((long) id).get();
        Assertions.assertEquals(updatedBrand.getName(), NAME_OF_BRAND_UPDATE);
    }

    @Test
    @Order(5)
    public void shouldBeDeletedById() {
        Random random = new Random();
        int id = random.nextInt(1, DEFAULT_SIZE + 1);
        brandService.delete((long) id);
        Assertions.assertEquals(brandService.findAll().size(), DEFAULT_SIZE);
    }

    public static Brand generateBrand(String name) {
        Brand brand = new Brand();
        brand.setName(name);
        return brand;
    }
}
