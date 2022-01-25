package ua.com.alevel.persistence.entity.sizes;

import ua.com.alevel.persistence.entity.BaseEntity;
import ua.com.alevel.persistence.entity.clothes.Clothes;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sizes")
public class Size extends BaseEntity {

    @Column(name = "size_name")
    private String sizeName;

    @ManyToMany(cascade = {
            CascadeType.PERSIST
    })
    @JoinTable(
            name = "size_thing",
            joinColumns = @JoinColumn(name = "size_id"),
            inverseJoinColumns = @JoinColumn(name = "thing_id"))
    private Set<Clothes> things;

    public Size() {
        super();
        this.things = new HashSet<>();
    }

    public void addThing(Clothes thing) {
        things.add(thing);
        thing.getSizes().add(this);
    }

    public void removeThing(Clothes thing) {
        things.remove(thing);
        thing.getSizes().remove(this);
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public Set<Clothes> getThings() {
        return things;
    }

    public void setThings(Set<Clothes> things) {
        this.things = things;
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
