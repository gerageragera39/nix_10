package ua.com.alevel.persistence.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "countries")
public class Countries extends BaseEntity {

    @Column(name = "country_name")
    private String nameOfCountry;

    private Integer ISO;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REMOVE
    })
    @JoinTable(
            name = "county_person",
            joinColumns = @JoinColumn(name = "county_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private Set<Population> people;

    public Countries() {
        super();
        this.people = new HashSet<>();
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

    public Set<Population> getPeople() {
        return people;
    }

    public void setPeople(Set<Population> people) {
        this.people = people;
    }

    public void addPerson(Population person) {
        people.add(person);
        person.getCountries().add(this);
    }

    public void removePerson(Population person) {
        people.remove(person);
        person.getCountries().remove(this);
    }

    @Override
    public String toString() {
        return "Countries{" +
                "nameOfCountry='" + nameOfCountry + '\'' +
                ", ISO=" + ISO +
                ", people=" + people +
                '}';
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
