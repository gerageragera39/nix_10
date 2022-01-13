package ua.com.alevel.persistence.entity;

import javax.persistence.*;

@Entity
@Table(name = "clothes")
public class Clothes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "thing_clg")
    private String thingCLG;

    private Double price;

    private Integer quantity;

    private Boolean isNew;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getThingCLG() {
        return thingCLG;
    }

    public void setThingCLG(String thingCLG) {
        this.thingCLG = thingCLG;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getNew() {
        return isNew;
    }

    public void setNew(Boolean aNew) {
        isNew = aNew;
    }
}
