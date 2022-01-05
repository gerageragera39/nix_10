package ua.com.alevel.facade;

import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.view.dto.request.AccountRequestDto;
import ua.com.alevel.view.dto.response.AccountResponseDto;
import ua.com.alevel.view.dto.response.PageData;

import java.util.Map;

public interface AccountFacade extends BaseFacade<AccountRequestDto, AccountResponseDto> {

    Map<Long, String> findTransactionsByAccountId(Long id);

    Map<Long, String> findUserByAccountId(Long id);

    void delete(String name);

    void writeOut(Long id);

    PageData<AccountResponseDto> findAll(Long userId, WebRequest request);
}
