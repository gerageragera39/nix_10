package ua.com.alevel.persistence.repository.users;

import org.springframework.stereotype.Repository;
import ua.com.alevel.persistence.entity.users.User;
import ua.com.alevel.persistence.repository.BaseRepository;

@Repository
public interface UserRepository<U extends User> extends BaseRepository<U> {

    U findByEmail(String email);

    boolean existsByEmail(String email);
}
