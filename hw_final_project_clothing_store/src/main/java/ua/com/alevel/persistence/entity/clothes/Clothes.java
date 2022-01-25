package ua.com.alevel.persistence.entity.clothes;

import ua.com.alevel.persistence.entity.BaseEntity;
import ua.com.alevel.persistence.entity.products.Product;
import ua.com.alevel.persistence.entity.brands.Brand;
import ua.com.alevel.persistence.entity.colors.Color;
import ua.com.alevel.persistence.entity.sizes.Size;
import ua.com.alevel.persistence.listener.ClothesVisibleGenerationListener;
import ua.com.alevel.persistence.sex.Sexes;
import ua.com.alevel.persistence.thing_type.ThingTypes;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clothes")
@EntityListeners(ClothesVisibleGenerationListener.class)
public class Clothes extends BaseEntity {

    private String title;

    @Enumerated(EnumType.STRING)
    private Sexes sex;

    @Enumerated(EnumType.STRING)
    private ThingTypes type;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String compound;

    @Column(name = "clg")
    private String CLG;

    private Double price;

    private Integer quantity;

    @OneToMany(mappedBy = "thing", cascade = CascadeType.REMOVE)
    private Set<Image> images;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @OneToMany(mappedBy = "wear")
    private Set<Product> products;

    @ManyToMany(mappedBy = "clothes", cascade = {
            CascadeType.PERSIST
    })
    private Set<Color> colors;

    @ManyToMany(mappedBy = "things", cascade = {
            CascadeType.PERSIST
    })
    private Set<Size> sizes;

    public Clothes() {
        super();
        this.images = new HashSet<>();
        this.price = (double) 0;
        this.quantity = 0;
        this.colors = new HashSet<>();
        this.sizes = new HashSet<>();
        this.products = new HashSet<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompound() {
        return compound;
    }

    public void setCompound(String compound) {
        this.compound = compound;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public Sexes getSex() {
        return sex;
    }

    public void setSex(Sexes sex) {
        this.sex = sex;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public ThingTypes getType() {
        return type;
    }

    public void setType(ThingTypes type) {
        this.type = type;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
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

    public void addImage(Image image) {
        images.add(image);
        image.setThing(this);
    }

    public void removeImage(Image image) {
        images.remove(image);
    }

    public String getCLG() {
        return CLG;
    }

    public void setCLG(String CLG) {
        this.CLG = CLG;
    }

    public Set<ua.com.alevel.persistence.entity.colors.Color> getColors() {
        return colors;
    }

    public void setColors(Set<ua.com.alevel.persistence.entity.colors.Color> colors) {
        this.colors = colors;
    }

    public Set<Size> getSizes() {
        return sizes;
    }

    public void setSizes(Set<Size> sizes) {
        this.sizes = sizes;
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
