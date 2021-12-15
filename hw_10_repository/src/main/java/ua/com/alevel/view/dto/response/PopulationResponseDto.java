package ua.com.alevel.view.dto.response;

import ua.com.alevel.persistence.entity.Population;
import ua.com.alevel.persistence.sex.Sex;

public class PopulationResponseDto extends ResponseDto {

    private String passportID;
    private String firstName;
    private String lastName;
    private int age;
    private Sex sex;
    private Integer countryCount;

    public PopulationResponseDto() {
    }

    public PopulationResponseDto(Population person) {
        setId(person.getId());
        setCreated(person.getCreated());
        setUpdated(person.getUpdated());
        setVisible(person.getVisible());
        this.passportID = person.getPassportID();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.age = person.getAge();
        this.sex = person.getSex();
    }

    public String getPassportID() {
        return passportID;
    }

    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Integer getCountryCount() {
        return countryCount;
    }

    public void setCountryCount(Integer countryCount) {
        this.countryCount = countryCount;
    }

    @Override
    public String toString() {
        return "PopulationResponseDto{" +
                "passportID='" + passportID + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", countryCount=" + countryCount +
                '}';
    }
}
