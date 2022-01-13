package ua.com.alevel.persistence.entity.clothes;

import ua.com.alevel.persistence.colors.Color;
import ua.com.alevel.persistence.entity.BaseEntity;
import ua.com.alevel.persistence.entity.brands.Brand;
import ua.com.alevel.persistence.listener.ClothesVisibleGenerationListener;
import ua.com.alevel.persistence.sex.Sexes;
import ua.com.alevel.persistence.sizes.Sizes;
import ua.com.alevel.persistence.thing_type.ThingTypes;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Entity
@Table(name = "clothes")
@EntityListeners(ClothesVisibleGenerationListener.class)
public class Clothes extends BaseEntity {

    private String title;

    @Enumerated(EnumType.STRING)
    private Color color;

    @Enumerated(EnumType.STRING)
    private Sizes size;

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

//    @Column(precision = 7, scale = 2)
    private Double price;

    private Integer quantity;

    @OneToMany(mappedBy = "thing")
    private Set<Image> images;

    @ManyToOne(cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    @JoinColumn(name = "brand_id")
    private Brand brand;

    public Clothes() {
        super();
        this.images = new HashSet<>();
//        this.price = new BigDecimal("00.00");
        this.price = (double) 0;
        this.quantity = 0;
        CLG = generateCLG();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Sizes getSize() {
        return size;
    }

    public void setSize(Sizes size) {
        this.size = size;
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

//    public BigDecimal getPrice() {
//        return price;
//    }
//
//    public void setPrice(BigDecimal price) {
//        this.price = price;
//    }

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

    private String generateCLG() {
        Random random = new Random();
        String CLG = " ";
        for (int i = 0; i < 4; i++) {
            String tempThreeDigits = String.valueOf(random.nextInt(1, 999));

            switch (tempThreeDigits.length()) {
                case 1:
                    tempThreeDigits = "00" + tempThreeDigits;
                    break;
                case 2:
                    tempThreeDigits = "0" + tempThreeDigits;
                    break;
            }
            if (i == 0) {
                CLG = " " + tempThreeDigits;
            } else {
                CLG += " " + tempThreeDigits;
            }
        }
        return CLG;
    }
}
