package ua.com.alevel.service.elastic.impl;

import ua.com.alevel.elastic.document.ClothesIndex;
import ua.com.alevel.service.elastic.ElasticClothesSearchService;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;


import java.util.ArrayList;
import java.util.List;

@Service
public class ElasticClothesSearchServiceImpl implements ElasticClothesSearchService {

    private static final String CLOTHES_INDEX = "clothesindex";
    private static final String THING_NAME = "thingName";
    private static final String STAR = "*";

    private final ElasticsearchOperations elasticsearchOperations;

    public ElasticClothesSearchServiceImpl(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    public List<String> searchClothesNames(String query) {
        QueryBuilder queryBuilder = QueryBuilders
                .wildcardQuery(THING_NAME, STAR + query.toLowerCase() + STAR);
        Query searchQuery = new NativeSearchQueryBuilder()
                .withFilter(queryBuilder)
                .withPageable(PageRequest.of(0, 5))
                .build();
        SearchHits<ClothesIndex> searchSuggestions =
                elasticsearchOperations.search(searchQuery,
                        ClothesIndex.class,
                        IndexCoordinates.of(CLOTHES_INDEX));
        final List<String> suggestions = new ArrayList<>();
        searchSuggestions.getSearchHits().forEach(searchHit -> suggestions.add(searchHit.getContent().getThingName()));
        return suggestions;
    }
}
