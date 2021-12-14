package ua.com.alevel.persistense.entity;

import ua.com.alevel.persistense.sex.Sex;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "population")
public class Population extends BaseEntity {

    @Column(name = "passport_id")
    private String passportID;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @ManyToMany(mappedBy = "people", cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REMOVE
    })
    private Set<Countries> countries;

    public Population() {
        super();
        this.countries = new HashSet<>();
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

    public Set<Countries> getCountries() {
        return countries;
    }

    public void setCountries(Set<Countries> countries) {
        this.countries = countries;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
