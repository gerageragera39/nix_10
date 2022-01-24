package ua.com.alevel.web.dto.request.brands;

import ua.com.alevel.web.dto.request.RequestDto;

public class BrandsRequestDto extends RequestDto {

    private String name;
    private String logoUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
