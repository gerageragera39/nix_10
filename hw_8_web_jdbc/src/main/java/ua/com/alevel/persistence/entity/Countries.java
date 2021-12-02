package ua.com.alevel.persistence.entity;

public class Countries extends BaseEntity {

    private String nameOfCountry;
    private Integer ISO;

    public Countries() {
        super();
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
}
