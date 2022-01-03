package ua.com.alevel.facade;

import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.persistence.entity.Account;
import ua.com.alevel.view.dto.request.TransactionRequestDto;
import ua.com.alevel.view.dto.response.PageData;
import ua.com.alevel.view.dto.response.TransactionResponseDto;

import java.util.Map;

public interface TransactionFacade extends BaseFacade<TransactionRequestDto, TransactionResponseDto> {

    PageData<TransactionResponseDto> findAll(Class entityClass, Long entityId, WebRequest request);

    Map<Long, String> findByAccountByTransactionId(Long id);

    Map<Long, String> findUserByTransactionId(Long id);
}
