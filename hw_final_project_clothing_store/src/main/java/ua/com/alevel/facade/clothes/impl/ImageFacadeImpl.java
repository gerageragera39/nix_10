package ua.com.alevel.facade.clothes.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.clothes.ImageFacade;
import ua.com.alevel.persistence.entity.clothes.Image;
import ua.com.alevel.service.clothes.ClothesService;
import ua.com.alevel.service.image.ImageService;
import ua.com.alevel.web.dto.request.clothes.ImageRequestDto;
import ua.com.alevel.web.dto.response.PageData;
import ua.com.alevel.web.dto.response.clothes.ImageResponseDto;

@Service
public class ImageFacadeImpl implements ImageFacade {

    private final ImageService imageService;
    private final ClothesService clothesService;

    public ImageFacadeImpl(ImageService imageService, ClothesService clothesService) {
        this.imageService = imageService;
        this.clothesService = clothesService;
    }

    @Override
    public void create(ImageRequestDto dto) {
        Image image = new Image();
        image.setUrl(dto.getUrl());
        image.setThing(clothesService.findById(dto.getThingId()).get());
        imageService.create(image);
    }

    @Override
    public void update(ImageRequestDto imageRequestDto, Long id) {

    }

    @Override
    public void delete(Long id) {
        imageService.delete(id);
    }

    @Override
    public ImageResponseDto findById(Long id) {
        return null;
    }

    @Override
    public PageData<ImageResponseDto> findAll(WebRequest request) {
        return null;
    }
}
