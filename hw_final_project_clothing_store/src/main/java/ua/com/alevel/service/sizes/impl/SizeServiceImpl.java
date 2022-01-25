package ua.com.alevel.service.sizes.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.entity.sizes.Size;
import ua.com.alevel.persistence.repository.sizes.SizeRepository;
import ua.com.alevel.service.sizes.SizeService;

import java.util.List;
import java.util.Optional;

@Service
public class SizeServiceImpl implements SizeService {

    private final SizeRepository sizeRepository;
    private final CrudRepositoryHelper<Size, SizeRepository> crudRepositoryHelper;

    public SizeServiceImpl(SizeRepository sizeRepository, CrudRepositoryHelper<Size, SizeRepository> crudRepositoryHelper) {
        this.sizeRepository = sizeRepository;
        this.crudRepositoryHelper = crudRepositoryHelper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Size> findAll() {
        return sizeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Size findByName(String sizeName) {
        Optional<Size> optionalSize = sizeRepository.findSizeBySizeName(sizeName);
        if (optionalSize.isPresent()) {
            return optionalSize.get();
        }
        return null;
    }

    @Override
    public void update(Size size) {
        crudRepositoryHelper.update(sizeRepository, size);
    }
}
