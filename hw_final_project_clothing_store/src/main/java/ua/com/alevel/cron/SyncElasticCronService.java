package ua.com.alevel.cron;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.com.alevel.elastic.document.ClothesIndex;
import ua.com.alevel.elastic.repository.ClothesIndexRepository;
import ua.com.alevel.persistence.repository.clothes.ClothesRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class SyncElasticCronService {

    private final ElasticsearchOperations elasticsearchOperations;
    private final ClothesRepository clothesRepository;
    private final ClothesIndexRepository clothesIndexRepository;

    public SyncElasticCronService(ElasticsearchOperations elasticsearchOperations, ClothesRepository clothesRepository, ClothesIndexRepository clothesIndexRepository) {
        this.elasticsearchOperations = elasticsearchOperations;
        this.clothesRepository = clothesRepository;
        this.clothesIndexRepository = clothesIndexRepository;
    }


    //    @Scheduled(fixedDelay = 60000)
    @Scheduled(cron = "0 0 12 * * ?")
    public void syncToSupplier() {
        elasticsearchOperations.indexOps(ClothesIndex.class).refresh();
        clothesIndexRepository.deleteAll();
        clothesIndexRepository.saveAll(prepareDataset());
    }

    private Collection<ClothesIndex> prepareDataset() {
        int page = -1;
        int size = 10;
        List<String> names = new ArrayList<>();
        while (true) {
            page++;
            Pageable pageable = PageRequest.of(page, size);
            List<String> strings = clothesRepository.findAllNames(pageable);
            if (strings != null && strings.size() != 0) {
                names.addAll(strings);
            } else {
                break;
            }
        }
        List<ClothesIndex> clothesIndices = new ArrayList<>();
        for (String name : names) {
            ClothesIndex clothesIndex = new ClothesIndex();
            clothesIndex.setThingName(name);
            clothesIndices.add(clothesIndex);
        }
        return clothesIndices;
    }
}
