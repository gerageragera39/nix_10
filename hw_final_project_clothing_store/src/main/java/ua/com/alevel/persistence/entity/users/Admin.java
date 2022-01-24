package ua.com.alevel.persistence.entity.users;

import ua.com.alevel.persistence.types.RoleType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {

    public Admin() {
        super();
        setRoleType(RoleType.ROLE_ADMIN);
    }
}
