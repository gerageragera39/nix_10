package ua.com.alevel.persistence.entity.brands;

import ua.com.alevel.persistence.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "logos")
public class Logo extends BaseEntity {

    private String url;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "brand_id")
    private Brand brand;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
