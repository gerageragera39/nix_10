package ua.com.alevel.persistence.entity.colors;

import ua.com.alevel.persistence.entity.BaseEntity;
import ua.com.alevel.persistence.entity.clothes.Clothes;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "colors")
public class Color extends BaseEntity {

    @Column(name = "color_name")
    private String colorName;

    @ManyToMany(cascade = {
            CascadeType.PERSIST
    })
    @JoinTable(
            name = "color_thing",
            joinColumns = @JoinColumn(name = "color_id"),
            inverseJoinColumns = @JoinColumn(name = "thing_id"))
    private Set<Clothes> clothes;

    public Color() {
        super();
        this.clothes = new HashSet<>();
    }

    public void addThing(Clothes thing) {
        clothes.add(thing);
        thing.getColors().add(this);
    }

    public void removeThing(Clothes thing) {
        clothes.remove(thing);
        thing.getColors().remove(this);
    }

    public String getColorName() {
        return colorName;
    }

    public Set<Clothes> getClothes() {
        return clothes;
    }

    public void setClothes(Set<Clothes> clothes) {
        this.clothes = clothes;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
