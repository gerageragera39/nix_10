package ua.com.alevel.service.supplier.impl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.com.alevel.cron.model.ClothesSupplierModel;
import ua.com.alevel.cron.model.TokenSupplierModel;
import ua.com.alevel.persistence.entity.clothes.Clothes;
import ua.com.alevel.persistence.entity.token.Token;
import ua.com.alevel.persistence.repository.clothes.ClothesRepository;
import ua.com.alevel.persistence.repository.token.TokenRepository;
import ua.com.alevel.service.supplier.SupplierService;

import java.util.Optional;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final ClothesRepository clothesRepository;
    private final TokenRepository tokenRepository;

    public SupplierServiceImpl(ClothesRepository clothesRepository, TokenRepository tokenRepository) {
        this.clothesRepository = clothesRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void syncToSupplier() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        Optional<Token> tokenOptionalContent = tokenRepository.findTokenById(1L);
        String token;
        String url;
        if (tokenOptionalContent.isPresent()) {
            token = tokenOptionalContent.get().getContent();
            url = tokenOptionalContent.get().getUrl();
        } else {
            token = "accepted";
            url = "http://localhost:8081/api/clothes";
        }
        headers.set("x_auth_token", token);
        HttpEntity httpEntity = new HttpEntity(headers);
        ResponseEntity<ClothesSupplierModel[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity,
                ClothesSupplierModel[].class
        );
        if (response.getStatusCodeValue() == 200) {
            ClothesSupplierModel[] clothesSuppliers = response.getBody();
            if (clothesSuppliers != null) {
                for (ClothesSupplierModel clothesSupplierModel : clothesSuppliers) {
                    Optional<Clothes> clothesOptional = clothesRepository.findClothesByCLG(clothesSupplierModel.getThingCLG());
                    if (clothesOptional.isPresent()) {
                        Clothes thing = clothesOptional.get();
                        thing.setQuantity(thing.getQuantity() + clothesSupplierModel.getQuantity());
                        thing.setPrice(clothesSupplierModel.getPrice());
                        clothesRepository.save(thing);
                    }
                }
            }
        }

        ResponseEntity<TokenSupplierModel> responseToken = restTemplate.exchange(
                url + "/new",
                HttpMethod.GET,
                httpEntity,
                TokenSupplierModel.class
        );
        if (responseToken.getStatusCodeValue() == 200) {
            TokenSupplierModel tokenSupplierModel = responseToken.getBody();
            if (tokenSupplierModel != null) {
                Optional<Token> tokenOptional = tokenRepository.findTokenById(1L);
                if (tokenOptional.isPresent()) {
                    Token tempToken = tokenOptional.get();
                    tempToken.setContent(tokenSupplierModel.getContent());
                    tokenRepository.save(tempToken);
                }
            }
        }
    }
}
