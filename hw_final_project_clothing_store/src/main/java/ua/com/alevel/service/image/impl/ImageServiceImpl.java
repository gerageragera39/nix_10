package ua.com.alevel.service.image.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.clothes.Image;
import ua.com.alevel.service.image.ImageService;

import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    @Override
    public void create(Image entity) {

    }

    @Override
    public void update(Image entity) {

    }

    @Override
    public void delete(Long id) {

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
