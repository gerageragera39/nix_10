package ua.com.alevel.web.dto.response.clothes;

import ua.com.alevel.persistence.colors.Color;
import ua.com.alevel.persistence.entity.clothes.Clothes;
import ua.com.alevel.persistence.entity.clothes.Image;
import ua.com.alevel.persistence.sex.Sexes;
import ua.com.alevel.persistence.sizes.Sizes;
import ua.com.alevel.persistence.thing_type.ThingTypes;
import ua.com.alevel.web.dto.response.ResponseDto;

import java.math.BigDecimal;
import java.util.List;

public class ClothesResponseDto extends ResponseDto {

    private String title;
    private Color color;
    private Sizes size;
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

    public ClothesResponseDto() { }

    public ClothesResponseDto(Clothes thing) {
        setId(thing.getId());
        setCreated(thing.getCreated());
        setUpdated(thing.getUpdated());
        setVisible(thing.getVisible());
        this.title = thing.getTitle();
        this.color = thing.getColor();
        this.size = thing.getSize();
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

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    private String generatePrice(Double price) {
        String stringPrice = price.toString();
        String[] finances = stringPrice.split("\\.");
        stringPrice = finances[0];
        if(finances[1].length() > 2) {
            finances[1] = String.valueOf(finances[1].charAt(0) + finances[1].charAt(1));
        }
        stringPrice += "." + finances[1];
        if(finances[1].length() < 2) {
            stringPrice += "0";
        }
        return stringPrice;
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
}
