package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.TransactionFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Transaction;
import ua.com.alevel.service.CategoryService;
import ua.com.alevel.service.TransactionService;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.view.dto.request.PageAndSizeData;
import ua.com.alevel.view.dto.request.SortData;
import ua.com.alevel.view.dto.request.TransactionRequestDto;
import ua.com.alevel.view.dto.response.PageData;
import ua.com.alevel.view.dto.response.TransactionResponseDto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransactionFacadeImpl implements TransactionFacade {

    private final TransactionService transactionService;
    private final CategoryService categoryService;

    public TransactionFacadeImpl(TransactionService transactionService, CategoryService categoryService) {
        this.transactionService = transactionService;
        this.categoryService = categoryService;
    }

    @Override
    public void create(TransactionRequestDto transactionRequestDto, String tempField) {
        Transaction transaction = new Transaction();

        Double balance;
        Double hryvnas;
        if (transactionRequestDto.getHryvnas() == null) {
            hryvnas = (double) 0;
        } else {
            hryvnas = transactionRequestDto.getHryvnas();
        }
        if (transactionRequestDto.getPenny() == null) {
            balance = (double) 0;
        } else {
            balance = transactionRequestDto.getPenny();
        }
        while (balance > 100) {
            balance -= 100;
            hryvnas++;
        }
        if (balance == 100) {
            hryvnas++;
            balance = (double) 0;
        } else {
            balance /= 100;
        }
        balance += hryvnas;
        if (balance == 0) {
            throw new NullPointerException("Null balance");
        }
        transaction.setAmount(balance);

        transaction.setCategory(categoryService.findByName(transactionRequestDto.getCategoryName()));
        transactionService.create(transaction, tempField);
    }

    @Override
    public void update(TransactionRequestDto transactionRequestDto, Long id) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public TransactionResponseDto findById(Long id) {
        return new TransactionResponseDto(transactionService.findById(id));
    }

    @Override
    public PageData<TransactionResponseDto> findAll(WebRequest request) {
        PageAndSizeData pageAndSizeData = WebRequestUtil.generatePageAndSizeData(request);
        SortData sortData = WebRequestUtil.generateSortData(request);
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setPageSize(pageAndSizeData.getSize());
        dataTableRequest.setCurrentPage(pageAndSizeData.getPage());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setOrder(sortData.getOrder());

        DataTableResponse<Transaction> dataTableResponse = transactionService.findAll(dataTableRequest);

        List<TransactionResponseDto> transactions = dataTableResponse.getItems().stream().
                map(TransactionResponseDto::new).
                collect(Collectors.toList());


        PageData<TransactionResponseDto> pageData = new PageData<>();
        pageData.setItems(transactions);
        pageData.setCurrentPage(pageAndSizeData.getPage());
        pageData.setPageSize(pageAndSizeData.getSize());
        pageData.setSort(sortData.getSort());
        pageData.setOrder(sortData.getOrder());
        pageData.setItemsSize(dataTableResponse.getItemsSize());
        pageData.initPaginationState();
        return pageData;
    }

    @Override
    public PageData<TransactionResponseDto> findAll(Class entityClass, Long entityId, WebRequest request) {
        PageAndSizeData pageAndSizeData = WebRequestUtil.generatePageAndSizeData(request);
        SortData sortData = WebRequestUtil.generateSortData(request);
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setPageSize(pageAndSizeData.getSize());
        dataTableRequest.setCurrentPage(pageAndSizeData.getPage());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setOrder(sortData.getOrder());

        DataTableResponse<Transaction> dataTableResponse = transactionService.findAll(entityClass, entityId, dataTableRequest);

        List<TransactionResponseDto> transactions = dataTableResponse.getItems().stream().
                map(TransactionResponseDto::new).
                collect(Collectors.toList());


        PageData<TransactionResponseDto> pageData = new PageData<>();
        pageData.setItems(transactions);
        pageData.setCurrentPage(pageAndSizeData.getPage());
        pageData.setPageSize(pageAndSizeData.getSize());
        pageData.setSort(sortData.getSort());
        pageData.setOrder(sortData.getOrder());
        pageData.setItemsSize(dataTableResponse.getItemsSize());
        pageData.initPaginationState();
        return pageData;
    }

    @Override
    public Map<Long, String> findByAccountByTransactionId(Long id) {
        return transactionService.findByAccountByTransactionId(id);
    }

    @Override
    public Map<Long, String> findUserByTransactionId(Long id) {
        return transactionService.findUserByTransactionId(id);
    }
}
