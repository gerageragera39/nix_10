package ua.com.alevel.web.dto.request.clothes;

import ua.com.alevel.persistence.colors.Color;
import ua.com.alevel.persistence.entity.clothes.Image;
import ua.com.alevel.persistence.sex.Sexes;
import ua.com.alevel.persistence.sizes.Sizes;
import ua.com.alevel.persistence.thing_type.ThingTypes;
import ua.com.alevel.web.dto.request.RequestDto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClothesRequestDto extends RequestDto {

    private String title;
    private Color color;
    private Sizes size;
    private Sexes sex;
    private ThingTypes type;
    private String description;
    private String compound;
    private Set<Image> images;
    private String brandName;

    public ClothesRequestDto() {
        this.images = new HashSet<>();
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

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
