package ua.com.alevel.entity;

public class Countries {

    private String nameOfCountry;
    private Population[] people;

    public String getNameOfCountry() {
        return nameOfCountry;
    }

    public void setNameOfCountry(String nameOfCountry) {
        this.nameOfCountry = nameOfCountry;
    }

    public Population[] getPeople() {
        return people;
    }

    public void setPeople(Population[] people) {
        this.people = people;
    }
}
