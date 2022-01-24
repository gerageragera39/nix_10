package ua.com.alevel.persistence.repository.token;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.alevel.persistence.entity.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Token findTokenById(Long id);
}
