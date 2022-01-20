package ua.com.alevel.persistence.entity.users;

import org.joda.time.LocalDate;
import ua.com.alevel.persistence.entity.products.Product;
import ua.com.alevel.persistence.listener.AgeByBirthDayGenerationListener;
import ua.com.alevel.persistence.listener.FullNameGenerationListener;
import ua.com.alevel.persistence.types.RoleType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("PERSONAL")
@EntityListeners({
        FullNameGenerationListener.class,
        AgeByBirthDayGenerationListener.class
})
public class Personal extends User {

    @Column(name = "birth_day")
    private String birthDay;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "personal")
    private Set<Product> products;

    @Transient
    private String fullName;

    @Transient
    private Integer age;

    public Personal() {
        super();
        this.products = new HashSet<>();
        setRoleType(RoleType.ROLE_PERSONAL);
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }
}
