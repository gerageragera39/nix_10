package ua.com.alevel.web.dto.response.users;

import ua.com.alevel.persistence.entity.users.Personal;
import ua.com.alevel.persistence.types.RoleType;
import ua.com.alevel.web.dto.response.ResponseDto;

import java.util.Date;

public class PersonalResponseDto extends ResponseDto {

    private Date birthDay;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean enabled;
    private RoleType roleType;

//    private String fullName;
//    private Integer age;

    public PersonalResponseDto() { }

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
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
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
}
