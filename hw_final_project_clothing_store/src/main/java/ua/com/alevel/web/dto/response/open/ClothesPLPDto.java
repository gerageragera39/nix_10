package ua.com.alevel.web.dto.response.open;

import ua.com.alevel.persistence.entity.brands.Brand;
import ua.com.alevel.persistence.entity.clothes.Clothes;
import ua.com.alevel.persistence.entity.clothes.Image;
import ua.com.alevel.persistence.sex.Sexes;
import ua.com.alevel.persistence.thing_type.ThingTypes;
import ua.com.alevel.web.dto.response.ResponseDto;

import java.math.BigDecimal;
import java.util.List;


public class ClothesPLPDto extends ResponseDto {

    private String title;
    private Sexes sex;
    private ThingTypes type;
    private List<Image> images;
    private Double price;
    private Brand brand;
    private String brandName;
    private String stringPrice;

    public ClothesPLPDto(Clothes thing) {
        setId(thing.getId());
        setCreated(thing.getCreated());
        setUpdated(thing.getUpdated());
        setVisible(thing.getVisible());
        this.title = thing.getTitle();
        this.sex = thing.getSex();
        this.type = thing.getType();
        this.images = thing.getImages().stream().toList();
        this.price = thing.getPrice();
        this.stringPrice = generatePrice(thing.getPrice());
        this.brandName = thing.getBrand().getName();
        this.brand = thing.getBrand();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Sexes getSex() {
        return sex;
    }

    public void setSex(Sexes sex) {
        this.sex = sex;
    }

    public ThingTypes getType() {
        return type;
    }

    public void setType(ThingTypes type) {
        this.type = type;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getStringPrice() {
        return stringPrice;
    }

    public void setStringPrice(String stringPrice) {
        this.stringPrice = stringPrice;
    }

    private String generatePrice(Double price) {
        String stringPrice = price.toString();
        String[] finances = stringPrice.split("\\.");
        stringPrice = finances[0];
        if (finances[1].length() > 2) {
            finances[1] = Character.toString(finances[1].charAt(0)) + Character.toString(finances[1].charAt(1));
        }
        stringPrice += "." + finances[1];
        if (finances[1].length() < 2) {
            stringPrice += "0";
        }
        return stringPrice;
    }
}
