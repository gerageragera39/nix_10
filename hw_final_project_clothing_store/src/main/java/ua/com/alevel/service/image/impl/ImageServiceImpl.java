package ua.com.alevel.service.image.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.clothes.Image;
import ua.com.alevel.persistence.repository.clothes.ImageRepository;
import ua.com.alevel.service.image.ImageService;

import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final CrudRepositoryHelper<Image, ImageRepository> crudRepositoryHelper;

    public ImageServiceImpl(
            ImageRepository imageRepository,
            CrudRepositoryHelper<Image, ImageRepository> crudRepositoryHelper) {
        this.imageRepository = imageRepository;
        this.crudRepositoryHelper = crudRepositoryHelper;
    }

    @Override
    public void create(Image entity) {
        if (StringUtils.isNotBlank(entity.getUrl())) {
            crudRepositoryHelper.create(imageRepository, entity);
        }
    }

    @Override
    public void update(Image entity) {

    }

    @Override
    public void delete(Long id) {
        crudRepositoryHelper.delete(imageRepository, id);
    }

    @Override
    public Optional<Image> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public DataTableResponse<Image> findAll(DataTableRequest request) {
        return null;
    }
}
