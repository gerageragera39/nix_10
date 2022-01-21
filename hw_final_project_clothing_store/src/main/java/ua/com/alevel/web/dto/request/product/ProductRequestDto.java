package ua.com.alevel.web.dto.request.product;

import ua.com.alevel.web.dto.request.RequestDto;

public class ProductRequestDto extends RequestDto {

    private String personalEmail;

    private Long wearId;

    private Integer count;

    private String clg;

    private String color;

    private String size;

    public ProductRequestDto() {
        this.count = 1;
    }

    public String getClg() {
        return clg;
    }

    public void setClg(String clg) {
        this.clg = clg;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getPersonalEmail() {
        return personalEmail;
    }

    public void setPersonalEmail(String personalEmail) {
        this.personalEmail = personalEmail;
    }

    public Long getWearId() {
        return wearId;
    }

    public void setWearId(Long wearId) {
        this.wearId = wearId;
    }
}
