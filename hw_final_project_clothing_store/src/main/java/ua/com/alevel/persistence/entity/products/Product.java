package ua.com.alevel.persistence.entity.products;

import ua.com.alevel.persistence.entity.BaseEntity;
import ua.com.alevel.persistence.entity.clothes.Clothes;
import ua.com.alevel.persistence.entity.users.Personal;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    private Integer count;

    private String clg;

    private String color;

    private String size;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "personal_id")
    private Personal personal;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "wear_id")
    private Clothes wear;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getClg() {
        return clg;
    }

    public void setClg(String clg) {
        this.clg = clg;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Clothes getWear() {
        return wear;
    }

    public void setWear(Clothes wear) {
        this.wear = wear;
    }

    public Personal getPersonal() {
        return personal;
    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }
}
