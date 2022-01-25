package ua.com.alevel.persistence.repository.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.alevel.persistence.entity.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Token findTokenById(Long id);
}
