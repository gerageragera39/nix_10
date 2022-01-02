package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.CategoryFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Account;
import ua.com.alevel.persistence.entity.Category;
import ua.com.alevel.service.CategoryService;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.view.dto.request.CategoryRequestDto;
import ua.com.alevel.view.dto.request.PageAndSizeData;
import ua.com.alevel.view.dto.request.SortData;
import ua.com.alevel.view.dto.response.AccountResponseDto;
import ua.com.alevel.view.dto.response.CategoryResponseDto;
import ua.com.alevel.view.dto.response.PageData;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryFacadeImpl implements CategoryFacade {

    private final CategoryService categoryService;

    public CategoryFacadeImpl(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public void create(CategoryRequestDto categoryRequestDto, String tempField) {
        Category category = new Category();
        category.setName(categoryRequestDto.getName());
        category.setFinances(categoryRequestDto.getFinances());
        categoryService.create(category, tempField);
    }

    @Override
    public void update(CategoryRequestDto categoryRequestDto, Long id) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public CategoryResponseDto findById(Long id) {
        return null;
    }

    @Override
    public PageData<CategoryResponseDto> findAll(WebRequest request) {
        PageAndSizeData pageAndSizeData = WebRequestUtil.generatePageAndSizeData(request);
        SortData sortData = WebRequestUtil.generateSortData(request);
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setPageSize(pageAndSizeData.getSize());
        dataTableRequest.setCurrentPage(pageAndSizeData.getPage());
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setOrder(sortData.getOrder());

        DataTableResponse<Category> dataTableResponse = categoryService.findAll(dataTableRequest);
        List<CategoryResponseDto> categories = dataTableResponse.getItems().stream().
                map(CategoryResponseDto::new).
                peek(categoryResponseDto -> categoryResponseDto.setTransactionCount((Integer) dataTableResponse.
                        getOtherParamMap().get(categoryResponseDto.getId()))).
                collect(Collectors.toList());

        PageData<CategoryResponseDto> pageData = new PageData<>();
        pageData.setItems(categories);
        pageData.setCurrentPage(pageAndSizeData.getPage());
        pageData.setPageSize(pageAndSizeData.getSize());
        pageData.setSort(sortData.getSort());
        pageData.setOrder(sortData.getOrder());
        pageData.setItemsSize(dataTableResponse.getItemsSize());
        pageData.initPaginationState();
        return pageData;
    }

    @Override
    public List<String> getAllCategoryNames() {
        return categoryService.getAllCategoryNames();
    }
}
