package ua.com.alevel.web.dto.request.clothes;

import ua.com.alevel.web.dto.request.RequestDto;

public class ImageRequestDto extends RequestDto {

    private String url;

    private Long thingId;

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
