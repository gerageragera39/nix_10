package ua.com.alevel.service.elastic;

import java.util.List;

public interface ElasticClothesSearchService {

    List<String> searchClothesNames(String query);
}
