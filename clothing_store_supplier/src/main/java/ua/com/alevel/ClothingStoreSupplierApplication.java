package ua.com.alevel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import ua.com.alevel.persistence.entity.Clothes;
import ua.com.alevel.persistence.entity.Token;
import ua.com.alevel.persistence.repository.ClothesRepository;
import ua.com.alevel.persistence.repository.token.TokenRepository;

import java.util.Optional;

@SpringBootApplication
public class ClothingStoreSupplierApplication {

    private final ClothesRepository clothesRepository;
    private final TokenRepository tokenRepository;

    @Value("${init}")
    private boolean initBool;

    public ClothingStoreSupplierApplication(ClothesRepository clothesRepository, TokenRepository tokenRepository) {
        this.clothesRepository = clothesRepository;
        this.tokenRepository = tokenRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(ClothingStoreSupplierApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        if(initBool){
            for (int i = 1; i < 3; i++) {
                Clothes clothes = new Clothes();
                clothes.setThingCLG(String.valueOf(i));
                clothes.setQuantity(i+5);
                clothes.setPrice((double) i*100);
                clothesRepository.save(clothes);
            }
        }

        Token tokenFind = tokenRepository.findTokenById(1L);
        if(tokenFind == null){
            Token token = new Token();
            token.setContent("accepted");
            tokenRepository.save(token);
        }
    }
}
