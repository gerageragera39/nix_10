package ua.com.alevel.web.dto.response.users;

import ua.com.alevel.persistence.entity.products.Product;
import ua.com.alevel.persistence.entity.users.Personal;
import ua.com.alevel.persistence.types.RoleType;
import ua.com.alevel.web.dto.response.ResponseDto;

import java.util.List;

public class PersonalResponseDto extends ResponseDto {

    private String birthDay;
    private Integer age;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private Boolean enabled;
    private RoleType roleType;
    private List<Product> products;

    public PersonalResponseDto() {
    }

    public PersonalResponseDto(Personal personal) {
        setId(personal.getId());
        setCreated(personal.getCreated());
        setUpdated(personal.getUpdated());
        setVisible(personal.getVisible());
        this.birthDay = personal.getBirthDay();
        this.firstName = personal.getFirstName();
        this.lastName = personal.getLastName();
        this.email = personal.getEmail();
        this.enabled = personal.getEnabled();
        this.roleType = personal.getRoleType();
        this.fullName = personal.getFullName();
        this.age = personal.getAge();
        this.products = personal.getProducts().stream().toList();
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
