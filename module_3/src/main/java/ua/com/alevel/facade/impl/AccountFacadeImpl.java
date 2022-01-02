package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.AccountFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Account;
import ua.com.alevel.service.AccountService;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.view.dto.request.AccountRequestDto;
import ua.com.alevel.view.dto.request.PageAndSizeData;
import ua.com.alevel.view.dto.request.SortData;
import ua.com.alevel.view.dto.response.AccountResponseDto;
import ua.com.alevel.view.dto.response.PageData;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AccountFacadeImpl implements AccountFacade {

    private final AccountService accountService;

    public AccountFacadeImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void create(AccountRequestDto accountRequestDto, String tempField) {
        Account account = new Account();
        account.setCardType(accountRequestDto.getCardType());
        Double balance;
        Double hryvnas;
        if (accountRequestDto.getHryvnas() == null) {
            hryvnas = Double.valueOf(0);
        } else{
            hryvnas = accountRequestDto.getHryvnas();
        }
        if (accountRequestDto.getPenny() == null) {
            balance = Double.valueOf(0);
        } else{
            balance = accountRequestDto.getPenny();
        }
        while (balance >= 100){
            balance = balance/100;
        }
        balance += hryvnas;
        account.setBalance(balance);
        accountService.create(account, tempField);
    }

    @Override
    public void update(AccountRequestDto accountRequestDto, Long id) {

    }

    @Override
    public void delete(Long id) {
        accountService.delete(id);
    }

    @Override
    public void delete(String name) {
        accountService.delete(name);
    }

    @Override
    public AccountResponseDto findById(Long id) {
        return new AccountResponseDto(accountService.findById(id));
    }

    @Override
    public PageData<AccountResponseDto> findAll(WebRequest request) {
        PageAndSizeData pageAndSizeData = WebRequestUtil.generatePageAndSizeData(request);
        SortData sortData = WebRequestUtil.generateSortData(request);
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setPageSize(pageAndSizeData.getSize());
        dataTableRequest.setCurrentPage(pageAndSizeData.getPage());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setOrder(sortData.getOrder());

        DataTableResponse<Account> dataTableResponse = accountService.findAll(dataTableRequest);
        List<AccountResponseDto> accounts = dataTableResponse.getItems().stream().
                map(AccountResponseDto::new).
                peek(accountResponseDto -> accountResponseDto.setTransactionCount((Integer) dataTableResponse.
                        getOtherParamMap().get(accountResponseDto.getId()))).
                collect(Collectors.toList());

        PageData<AccountResponseDto> pageData = new PageData<>();
        pageData.setItems(accounts);
        pageData.setCurrentPage(pageAndSizeData.getPage());
        pageData.setPageSize(pageAndSizeData.getSize());
        pageData.setSort(sortData.getSort());
        pageData.setOrder(sortData.getOrder());
        pageData.setItemsSize(dataTableResponse.getItemsSize());
        pageData.initPaginationState();
        return pageData;
    }

    @Override
    public Map<Long, String> findTransactionsByAccountId(Long id) {
        return accountService.findTransactionsByAccountId(id);
    }

    @Override
    public Map<Long, String> findUserByAccountId(Long id) {
        return accountService.findUserByAccountId(id);
    }
}
