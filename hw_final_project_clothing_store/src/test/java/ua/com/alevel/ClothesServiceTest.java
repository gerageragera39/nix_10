package ua.com.alevel;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ua.com.alevel.persistence.entity.clothes.Clothes;
import ua.com.alevel.persistence.sex.Sexes;
import ua.com.alevel.persistence.thing_type.ThingTypes;
import ua.com.alevel.service.clothes.ClothesService;

import java.util.Random;

@SpringBootTest(classes = ClothingStoreApplication.class)
@TestPropertySource("/application.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClothesServiceTest {

    public static final String NAME_OF_THING = "testThingName";
    private static final String NAME_OF_THING_UPDATE = "testThingName_update";
    private static final int DEFAULT_SIZE = 10;

    @Autowired
    private ClothesService clothesService;

    @Test
    @Order(1)
    public void setUp() {
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            Clothes clothes = generateThing(NAME_OF_THING + i, String.valueOf(i));
            clothesService.create(clothes);
        }
        Assertions.assertEquals(clothesService.findAll().size(), DEFAULT_SIZE);
    }

    @Test
    @Order(2)
    public void shouldBeCreatedWhenNameIsNotDuplicate() {
        Clothes clothes = generateThing(NAME_OF_THING + (DEFAULT_SIZE + 1), String.valueOf(DEFAULT_SIZE + 1));
        clothesService.create(clothes);
        Assertions.assertEquals(clothesService.findAll().size(), DEFAULT_SIZE + 1);
    }

    @Test
    @Order(3)
    public void shouldBeCreatedWhenNameIsDuplicate() {
        Clothes clothes = generateThing(NAME_OF_THING + 1, String.valueOf(1));
        clothesService.create(clothes);
        Assertions.assertEquals(clothesService.findAll().size(), DEFAULT_SIZE + 1);
    }

    @Test
    @Order(4)
    public void shouldBeUpdatedById() {
        Random random = new Random();
        int id = random.nextInt(1, DEFAULT_SIZE + 1);
        Clothes thing = clothesService.findById((long) id).get();
        thing.setTitle(NAME_OF_THING_UPDATE);
        clothesService.update(thing);
        Clothes updatedThing = clothesService.findById((long) id).get();
        Assertions.assertEquals(updatedThing.getTitle(), NAME_OF_THING_UPDATE);
    }

    @Test
    @Order(5)
    public void shouldBeDeletedById() {
        Random random = new Random();
        int id = random.nextInt(1, DEFAULT_SIZE + 1);
        clothesService.delete((long) id);
        Assertions.assertEquals(clothesService.findAll().size(), DEFAULT_SIZE);
    }

    public static Clothes generateThing(String name, String clg) {
        Clothes clothes = new Clothes();
        Random random = new Random();
        clothes.setCLG(String.valueOf(clg));
        clothes.setSex(Sexes.values()[random.nextInt(0,2)]);
        clothes.setType(ThingTypes.values()[random.nextInt(0, 9)]);
        clothes.setTitle(name);
        clothes.setCompound(name);
        clothes.setDescription(name);
        return clothes;
    }
}
