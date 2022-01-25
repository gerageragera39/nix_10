package ua.com.alevel.web.dto.response.clothes;

import ua.com.alevel.persistence.entity.clothes.Clothes;
import ua.com.alevel.persistence.entity.clothes.Image;
import ua.com.alevel.persistence.sex.Sexes;
import ua.com.alevel.persistence.thing_type.ThingTypes;
import ua.com.alevel.web.dto.response.ResponseDto;

import java.util.List;

public class ClothesResponseDto extends ResponseDto {

    private String title;
    private Sexes sex;
    private ThingTypes type;
    private String description;
    private String compound;
    private String brandName;
    private Long brandId;
    private List<Image> images;
    private Double price;
    private Integer quantity;
    private String stringPrice;
    private String clg;

    public ClothesResponseDto() {
    }

    public ClothesResponseDto(Clothes thing) {
        setId(thing.getId());
        setCreated(thing.getCreated());
        setUpdated(thing.getUpdated());
        setVisible(thing.getVisible());
        this.title = thing.getTitle();
        this.sex = thing.getSex();
        this.type = thing.getType();
        this.description = thing.getDescription();
        this.compound = thing.getCompound();
        this.images = thing.getImages().stream().toList();
        this.price = thing.getPrice();
        this.quantity = thing.getQuantity();
        this.stringPrice = generatePrice(thing.getPrice());
        this.brandName = thing.getBrand().getName();
        this.brandId = thing.getBrand().getId();
        this.clg = thing.getCLG();
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getStringPrice() {
        return stringPrice;
    }

    public void setStringPrice(String stringPrice) {
        this.stringPrice = stringPrice;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getClg() {
        return clg;
    }

    public void setClg(String clg) {
        this.clg = clg;
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
