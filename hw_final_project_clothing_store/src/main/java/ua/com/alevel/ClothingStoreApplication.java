package ua.com.alevel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ua.com.alevel.cron.SyncElasticCronService;
import ua.com.alevel.elastic.document.ClothesIndex;
import ua.com.alevel.persistence.entity.token.Token;
import ua.com.alevel.persistence.entity.users.Admin;
import ua.com.alevel.persistence.repository.token.TokenRepository;
import ua.com.alevel.persistence.repository.users.AdminRepository;

import javax.annotation.PreDestroy;
import java.util.Optional;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class })
public class ClothingStoreApplication {

    private final BCryptPasswordEncoder encoder;
    private final AdminRepository adminRepository;
    private final TokenRepository tokenRepository;
    private final ElasticsearchOperations elasticsearchOperations;
    private final SyncElasticCronService syncElasticCronService;

    public ClothingStoreApplication(BCryptPasswordEncoder encoder, AdminRepository adminRepository, TokenRepository tokenRepository, ElasticsearchOperations elasticsearchOperations, SyncElasticCronService syncElasticCronService) {
        this.encoder = encoder;
        this.adminRepository = adminRepository;
        this.tokenRepository = tokenRepository;
        this.elasticsearchOperations = elasticsearchOperations;
        this.syncElasticCronService = syncElasticCronService;
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
        syncElasticCronService.syncToSupplier();
    }

    @PreDestroy
    public void resetElastic() {
        elasticsearchOperations.indexOps(ClothesIndex.class).delete();
    }
}
