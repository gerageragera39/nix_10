package ua.com.alevel.facade.open;

import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.BaseFacade;
import ua.com.alevel.persistence.entity.clothes.Clothes;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.ResponseDto;

import java.util.List;

public interface PLPFacade<RES extends ResponseDto>{

    PageData<RES> findAll(WebRequest request);
}