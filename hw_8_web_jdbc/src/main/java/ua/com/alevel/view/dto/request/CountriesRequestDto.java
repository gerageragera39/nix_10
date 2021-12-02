package ua.com.alevel.view.dto.request;

public class CountriesRequestDto extends RequestDto{

    private String nameOfCountry;
    private Integer ISO;

    public String getNameOfCountry() {
        return nameOfCountry;
    }

    public void setNameOfCountry(String nameOfCountry) {
        this.nameOfCountry = nameOfCountry;
    }

    public Integer getISO() {
        return ISO;
    }

    public void setISO(Integer ISO) {
        this.ISO = ISO;
    }
}
