package ua.com.alevel.service.clothes.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.clothes.Clothes;
import ua.com.alevel.persistence.entity.clothes.Image;
import ua.com.alevel.persistence.entity.colors.Color;
import ua.com.alevel.persistence.entity.sizes.Size;
import ua.com.alevel.persistence.repository.clothes.ClothesRepository;
import ua.com.alevel.persistence.repository.clothes.ImageRepository;
import ua.com.alevel.persistence.repository.colors.ColorRepository;
import ua.com.alevel.persistence.repository.sizes.SizeRepository;
import ua.com.alevel.service.clothes.ClothesService;
import ua.com.alevel.util.WebResponseUtil;

import java.util.*;

@Service
public class ClothesServiceImpl implements ClothesService {

    private final CrudRepositoryHelper<Clothes, ClothesRepository> crudRepositoryHelper;
    private final ClothesRepository clothesRepository;
    private final ImageRepository imageRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;

    public ClothesServiceImpl(CrudRepositoryHelper<Clothes, ClothesRepository> crudRepositoryHelper, ClothesRepository clothesRepository, ImageRepository imageRepository, ColorRepository colorRepository, SizeRepository sizeRepository) {
        this.crudRepositoryHelper = crudRepositoryHelper;
        this.clothesRepository = clothesRepository;
        this.imageRepository = imageRepository;
        this.colorRepository = colorRepository;
        this.sizeRepository = sizeRepository;
    }

    @Override
    public void create(Clothes entity) {
        crudRepositoryHelper.create(clothesRepository, entity);
    }

    @Override
    public void update(Clothes entity) {
        crudRepositoryHelper.update(clothesRepository, entity);
    }

    @Override
    public void delete(Long id) {
        Optional<Clothes> optionalClothes = clothesRepository.findById(id);
        if(optionalClothes.isPresent()) {
            Clothes thing = optionalClothes.get();
            List<Color> colors = thing.getColors().stream().toList();
            List<Size> sizes = thing.getSizes().stream().toList();
            for (Color color : colors) {
                color.getClothes().remove(thing);
            }
            for (Size size : sizes) {
                size.getThings().remove(thing);
            }
            colorRepository.saveAll(colors);
            sizeRepository.saveAll(sizes);
            thing.getSizes().removeAll(colors);
            thing.getSizes().removeAll(sizes);
            clothesRepository.delete(thing);
        }
    }

    @Override
    public Optional<Clothes> findById(Long id) {
        return crudRepositoryHelper.findById(clothesRepository, id);
    }

    @Override
    public DataTableResponse<Clothes> findAll(DataTableRequest request) {
//        DataTableResponse<Clothes> dataTableResponse = crudRepositoryHelper.findAll(clothesRepository, request);
//        int count = clothesRepository.countAllByVisibleTrue();
////        WebUtil.initDataTableResponse(request, dataTableResponse, count);
//        return dataTableResponse;
//        int page = 0;
//        int size = 2;
//        Pageable pageable = PageRequest.of(page, size);
//        List<String> strings = clothesRepository.findAllNames(pageable);
        DataTableResponse<Clothes> dataTableResponse = crudRepositoryHelper.findAll(clothesRepository, request);
        long count = clothesRepository.countAllByVisibleTrue();
        WebResponseUtil.initDataTableResponse(request, dataTableResponse, count);
        return dataTableResponse;
    }

    @Override
    public List<Clothes> findAllByBrandId(Long id) {
        return clothesRepository.findAllByBrandId(id);
    }

    @Override
    public Map<Long, String> findColorsByThingId(Long id) {
        Map<Long, String> map = new HashMap<>();
        List<Color> colors = clothesRepository.findColorsByThingId(id);
        for (int i = 0; i < colors.size(); i++) {
            map.put(colors.get(i).getId(), colors.get(i).getColorName());
        }
        return map;
    }

    @Override
    public List<Color> findAllColors() {
        return colorRepository.findAll();
    }
}
