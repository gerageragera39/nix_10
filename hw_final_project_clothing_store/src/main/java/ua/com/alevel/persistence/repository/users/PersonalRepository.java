package ua.com.alevel.persistence.repository.users;

import org.springframework.stereotype.Repository;
import ua.com.alevel.persistence.entity.users.Personal;

@Repository
public interface PersonalRepository extends UserRepository<Personal> {
}
