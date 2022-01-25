package ua.com.alevel.service.brand.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.brands.Logo;
import ua.com.alevel.persistence.repository.brands.LogoRepository;
import ua.com.alevel.service.brand.LogoService;

import java.util.Optional;

@Service
public class LogoServiceImpl implements LogoService {

    private final LogoRepository logoRepository;
    private final CrudRepositoryHelper<Logo, LogoRepository> crudRepositoryHelper;

    public LogoServiceImpl(LogoRepository logoRepository,
                           CrudRepositoryHelper<Logo, LogoRepository> crudRepositoryHelper) {
        this.logoRepository = logoRepository;
        this.crudRepositoryHelper = crudRepositoryHelper;
    }

    @Override
    public void create(Logo entity) {
        crudRepositoryHelper.create(logoRepository, entity);
    }

    @Override
    public void update(Logo entity) {
        crudRepositoryHelper.update(logoRepository, entity);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Optional<Logo> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public DataTableResponse<Logo> findAll(DataTableRequest request) {
        return null;
    }
}
