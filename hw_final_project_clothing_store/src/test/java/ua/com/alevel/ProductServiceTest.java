package ua.com.alevel;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ua.com.alevel.persistence.entity.clothes.Clothes;
import ua.com.alevel.persistence.entity.products.Product;
import ua.com.alevel.persistence.entity.users.Personal;
import ua.com.alevel.persistence.sex.Sexes;
import ua.com.alevel.persistence.thing_type.ThingTypes;
import ua.com.alevel.service.clothes.ClothesService;
import ua.com.alevel.service.personal.PersonalService;
import ua.com.alevel.service.products.ProductService;

import java.util.List;
import java.util.Random;

@SpringBootTest(classes = ClothingStoreApplication.class)
@TestPropertySource("/application.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductServiceTest {

    private static final String E_MAIL = "root";
    private static final String PASSWORD = "rootroot";
    private static final String CLG = "12345";

    @Autowired
    private ClothesService clothesService;

    @Autowired
    private PersonalService personalService;


    @Autowired
    private ProductService productService;

    @Test
    @Order(1)
    public void setUp() {
        Personal personal = new Personal();
        personal.setEmail(E_MAIL);
        personal.setPassword(PASSWORD);

        Clothes clothes = new Clothes();
        clothes.setTitle("title");
        clothes.setCLG(CLG);
        clothes.setQuantity(100);
        personalService.create(personal);
        Personal personal1 = personalService.findByEmail(E_MAIL);
        Product product = generateProduct(personal1, clothes);
        productService.create(product);
        Assertions.assertEquals(productService.findByPersonalEmail(personalService.findByEmail(E_MAIL)).size(), 1);
    }

    @Test
    @Order(2)
    public void shouldBeCreatedWhenIsNotDuplicate() {
        Personal personal = personalService.findByEmail(E_MAIL);
        Clothes clothes = clothesService.findByClg(CLG).get();
        Product product = generateProduct(personal, clothes);
        productService.create(product);
        Personal personal1 = personalService.findByEmail(E_MAIL);
        Assertions.assertEquals(productService.findByPersonalEmail(personalService.findByEmail(E_MAIL)).size(), 1);
        Assertions.assertEquals(personal1.getProducts().stream().toList().get(0).getCount(), 2);
    }

    @Test
    @Order(3)
    public void shouldBeCreatedWhenNameIsDuplicate() {
        Personal personal = personalService.findByEmail(E_MAIL);
        Clothes clothes = clothesService.findByClg(CLG).get();
        Product product = new Product();
        product.setPersonal(personal);
        product.setWear(clothes);
        product.setClg(CLG);
        product.setColor("Red");
        product.setSize("M");
        productService.create(product);
        Assertions.assertEquals(productService.findByPersonalEmail(personalService.findByEmail(E_MAIL)).size(), 2);
    }

    @Test
    @Order(4)
    public void shouldBeDeletedById() {
        List<Product> productList = productService.findByPersonalEmail(personalService.findByEmail(E_MAIL));
        productService.delete(productList.get(0).getId());
        Assertions.assertEquals(productService.findByPersonalEmail(personalService.findByEmail(E_MAIL)).size(), 1);
    }

    public static Product generateProduct(Personal personal, Clothes clothes) {
        Product product = new Product();
        product.setCount(1);
        product.setWear(new Clothes());
        product.setPersonal(personal);
        product.setClg(CLG);
        product.setWear(clothes);
        product.setColor("Green");
        product.setSize("XS");
        return product;
    }
}
