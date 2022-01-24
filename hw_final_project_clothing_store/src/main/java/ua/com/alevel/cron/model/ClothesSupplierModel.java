package ua.com.alevel.cron.model;

public class ClothesSupplierModel {

    private Long id;
    private String thingCLG;
    private Double price;
    private Integer quantity;

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
}
