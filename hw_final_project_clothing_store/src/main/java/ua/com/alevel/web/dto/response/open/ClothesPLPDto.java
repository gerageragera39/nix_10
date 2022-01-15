package ua.com.alevel.web.dto.response.open;

import ua.com.alevel.persistence.colors.Color;
import ua.com.alevel.persistence.entity.clothes.Clothes;
import ua.com.alevel.persistence.entity.clothes.Image;
import ua.com.alevel.persistence.sex.Sexes;
import ua.com.alevel.persistence.sizes.Sizes;
import ua.com.alevel.persistence.thing_type.ThingTypes;
import ua.com.alevel.web.dto.response.ResponseDto;


public class ClothesPLPDto extends ResponseDto {

    private String title;
    private Color color;
    private Sizes size;
    private Sexes sex;
    private ThingTypes type;
    private Image image;
    private Double price;
    private String brandName;

    public ClothesPLPDto(Clothes thing) {
        setId(thing.getId());
        setCreated(thing.getCreated());
        setUpdated(thing.getUpdated());
        setVisible(thing.getVisible());
        this.title = thing.getTitle();
        this.color = thing.getColor();
        this.size = thing.getSize();
        this.sex = thing.getSex();
        this.type = thing.getType();
        this.image = thing.getImages().stream().toList().get(0);
        this.price = thing.getPrice();
        this.brandName = thing.getBrand().getName();
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image images) {
        this.image = images;
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
}
