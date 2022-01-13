package ua.com.alevel.persistence.listener;

import ua.com.alevel.persistence.entity.clothes.Clothes;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

public class ClothesVisibleGenerationListener {

    @PostLoad
    @PostPersist
    @PostUpdate
    public void generateClothesVisible(Clothes thing) {
        thing.setVisible(thing.getQuantity() != null &&
                thing.getQuantity() > 0 &&
                thing.getPrice() != null &&
                thing.getPrice() > 0);
    }
}
