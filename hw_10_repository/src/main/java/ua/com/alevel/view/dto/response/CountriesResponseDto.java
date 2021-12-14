package ua.com.alevel.view.dto.response;


import ua.com.alevel.persistense.entity.Countries;

public class CountriesResponseDto extends ResponseDto {

    private String nameOfCountry;
    private Integer ISO;
    private Integer peopleCount;

    public CountriesResponseDto() {
    }

    public CountriesResponseDto(Countries country) {
        setId(country.getId());
        setCreated(country.getCreated());
        setUpdated(country.getUpdated());
        setVisible(country.getVisible());
        this.nameOfCountry = country.getNameOfCountry();
        this.ISO = country.getISO();
    }

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

    public Integer getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(Integer peopleCount) {
        this.peopleCount = peopleCount;
    }
}
