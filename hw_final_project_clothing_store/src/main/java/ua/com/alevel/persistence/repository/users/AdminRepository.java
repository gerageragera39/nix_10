package ua.com.alevel.persistence.repository.users;

import org.springframework.stereotype.Repository;
import ua.com.alevel.persistence.entity.users.Admin;
import ua.com.alevel.persistence.repository.BaseRepository;

@Repository
public interface AdminRepository extends UserRepository<Admin> {
}
