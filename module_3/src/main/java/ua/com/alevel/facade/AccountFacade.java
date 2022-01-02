package ua.com.alevel.facade;

import ua.com.alevel.persistence.entity.Transaction;
import ua.com.alevel.view.dto.request.AccountRequestDto;
import ua.com.alevel.view.dto.response.AccountResponseDto;

import java.util.List;
import java.util.Map;

public interface AccountFacade extends BaseFacade<AccountRequestDto, AccountResponseDto> {

    Map<Long, String> findTransactionsByAccountId(Long id);

    Map<Long, String> findUserByAccountId(Long id);

    void delete(String name);
}
