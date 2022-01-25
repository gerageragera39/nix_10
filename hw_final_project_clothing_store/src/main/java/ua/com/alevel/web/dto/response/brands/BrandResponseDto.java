package ua.com.alevel.web.dto.response.brands;

import ua.com.alevel.persistence.entity.brands.Brand;
import ua.com.alevel.web.dto.response.ResponseDto;

public class BrandResponseDto extends ResponseDto {

    private String name;
    private String logo;

    public BrandResponseDto() {
    }

    public BrandResponseDto(Brand brand) {
        setId(brand.getId());
        setCreated(brand.getCreated());
        setUpdated(brand.getUpdated());
        setVisible(brand.getVisible());
        this.name = brand.getName();
        this.logo = brand.getLogo().getUrl();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
