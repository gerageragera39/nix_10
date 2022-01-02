package ua.com.alevel.facade;

import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.view.dto.request.TransactionRequestDto;
import ua.com.alevel.view.dto.response.PageData;
import ua.com.alevel.view.dto.response.TransactionResponseDto;

public interface TransactionFacade extends BaseFacade<TransactionRequestDto, TransactionResponseDto> {

    PageData<TransactionResponseDto> findAll(Long accountId, WebRequest request);
}
