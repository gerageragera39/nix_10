package ua.com.alevel.persistence.entity.brands;

import ua.com.alevel.persistence.entity.BaseEntity;
import ua.com.alevel.persistence.entity.clothes.Clothes;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "brands")
public class Brand extends BaseEntity {

    private String name;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.REMOVE)
    private Set<Clothes> clothes;

    @OneToOne(mappedBy = "brand", cascade = CascadeType.REMOVE)
    private Logo logo;

    public Brand() {
        super();
        this.clothes = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Clothes> getClothes() {
        return clothes;
    }

    public void setClothes(Set<Clothes> clothes) {
        this.clothes = clothes;
    }

    public void addClothes(Clothes thing) {
        clothes.add(thing);
        thing.setBrand(this);
    }

    public void removeClothes(Clothes thing) {
        clothes.remove(thing);
    }

    public Logo getLogo() {
        return logo;
    }

    public void setLogo(Logo logo) {
        this.logo = logo;
    }
}
