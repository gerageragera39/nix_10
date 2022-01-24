package ua.com.alevel.web.dto.response.clothes;

import ua.com.alevel.persistence.entity.clothes.Image;
import ua.com.alevel.web.dto.response.ResponseDto;

public class ImageResponseDto extends ResponseDto {

    private String url;

    private Long thingId;

    public ImageResponseDto(Image image) {
        setId(image.getId());
        setCreated(image.getCreated());
        setUpdated(image.getUpdated());
        setVisible(image.getVisible());
        this.thingId = image.getThing().getId();
        this.url = image.getUrl();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getThingId() {
        return thingId;
    }

    public void setThingId(Long thingId) {
        this.thingId = thingId;
    }
}
