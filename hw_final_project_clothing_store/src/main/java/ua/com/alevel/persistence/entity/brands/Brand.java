package ua.com.alevel.persistence.entity.brands;

import ua.com.alevel.persistence.entity.BaseEntity;
import ua.com.alevel.persistence.entity.clothes.Clothes;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "brands")
public class Brand extends BaseEntity {

    private String name;

    @OneToMany(mappedBy = "brand")
    private Set<Clothes> clothes;

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
}
