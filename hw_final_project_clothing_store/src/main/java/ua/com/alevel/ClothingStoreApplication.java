package ua.com.alevel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ua.com.alevel.persistence.entity.clothes.Clothes;
import ua.com.alevel.persistence.entity.token.Token;
import ua.com.alevel.persistence.entity.users.Admin;
import ua.com.alevel.persistence.repository.token.TokenRepository;
import ua.com.alevel.persistence.repository.users.AdminRepository;
import ua.com.alevel.persistence.thing_type.ThingTypes;

import java.util.Optional;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class })
public class ClothingStoreApplication {

    private final BCryptPasswordEncoder encoder;
    private final AdminRepository adminRepository;
    private final TokenRepository tokenRepository;

    public ClothingStoreApplication(BCryptPasswordEncoder encoder, AdminRepository adminRepository, TokenRepository tokenRepository) {
        this.encoder = encoder;
        this.adminRepository = adminRepository;
        this.tokenRepository = tokenRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(ClothingStoreApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void listen() {
        Admin admin = adminRepository.findByEmail("admin@mail.com");
        if (admin == null) {
            admin = new Admin();
            admin.setEmail("admin@mail.com");
            admin.setPassword(encoder.encode("rootroot"));
            adminRepository.save(admin);
        }
        Optional<Token> tokenOptional = tokenRepository.findTokenById(1L);
        if(tokenOptional.isEmpty()){
            Token token = new Token();
            token.setUrl("http://localhost:8081/api/clothes");
            token.setContent("accepted");
            tokenRepository.save(token);
        }

    }
}
