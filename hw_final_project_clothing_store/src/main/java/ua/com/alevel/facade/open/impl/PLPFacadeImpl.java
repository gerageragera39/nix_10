package ua.com.alevel.facade.open.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.open.PLPFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.clothes.Clothes;
import ua.com.alevel.service.elastic.ElasticClothesSearchService;
import ua.com.alevel.service.open.PLPService;
import ua.com.alevel.util.WebUtil;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.clothes.ClothesResponseDto;
import ua.com.alevel.web.dto.response.open.ClothesPLPDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PLPFacadeImpl implements PLPFacade {

    private final PLPService plpService;
    private final ElasticClothesSearchService elasticClothesSearchService;

    public PLPFacadeImpl(PLPService plpService, ElasticClothesSearchService elasticClothesSearchService) {
        this.plpService = plpService;
        this.elasticClothesSearchService = elasticClothesSearchService;
    }

    @Override
    public PageData findAll(WebRequest request) {

        DataTableRequest dataTableRequest = WebUtil.generatePLPDataTableRequestByWebRequest(request);
        DataTableResponse<Clothes> tableResponse = plpService.findAll(dataTableRequest);
        List<ClothesPLPDto> clothes = tableResponse.getItems().stream().
                map(ClothesPLPDto::new).
                collect(Collectors.toList());

        PageData<ClothesPLPDto> pageData = (PageData<ClothesPLPDto>) WebUtil.initPageData(tableResponse);
        pageData.setItems(clothes);
        return pageData;
    }

    @Override
    public ClothesResponseDto findById(Long id) {
        return new ClothesResponseDto(plpService.findById(id));
    }

    @Override
    public List<String> searchClothesNames(String query) {
        return elasticClothesSearchService.searchClothesNames(query);
    }
}
