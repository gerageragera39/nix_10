package ua.com.alevel.web.dto.response.products;

import ua.com.alevel.persistence.entity.clothes.Image;
import ua.com.alevel.persistence.entity.products.Product;
import ua.com.alevel.persistence.sex.Sexes;
import ua.com.alevel.persistence.thing_type.ThingTypes;
import ua.com.alevel.web.dto.response.ResponseDto;

public class ProductResponseDto extends ResponseDto {

    private String title;
    private String clg;
    private Image images;
    private String size;
    private Sexes sex;
    private String color;
    private ThingTypes type;
    private String price;
    private Integer count;

    public ProductResponseDto(Product product) {
        setId(product.getId());
        setCreated(product.getCreated());
        setUpdated(product.getUpdated());
        setVisible(product.getVisible());
        this.title = product.getWear().getTitle();
        this.sex = product.getWear().getSex();
        this.type = product.getWear().getType();
        this.images = product.getWear().getImages().stream().toList().get(0);
        this.price = generatePrice(product.getWear().getPrice());
        this.clg = product.getClg();
        this.color = product.getColor();
        this.size = product.getSize();
        this.count = product.getCount();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClg() {
        return clg;
    }

    public void setClg(String clg) {
        this.clg = clg;
    }

    public Image getImages() {
        return images;
    }

    public void setImages(Image images) {
        this.images = images;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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
