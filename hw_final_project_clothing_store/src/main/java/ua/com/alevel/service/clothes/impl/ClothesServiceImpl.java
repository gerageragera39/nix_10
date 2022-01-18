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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
//        crudRepositoryHelper.delete(clothesRepository, id);
        Optional<Clothes> optionalClothes = clothesRepository.findById(id);
        if(optionalClothes.isPresent()) {
            Clothes clothes = optionalClothes.get();
            List<Image> images = clothes.getImages().stream().toList();
            List<Color> colors = clothes.getColors().stream().toList();
            List<Size> sizes = clothes.getSizes().stream().toList();
            for (Color color : colors) {
                System.out.println(color.removeThing(clothes));
//                colorRepository.save(color);
            }
            for (Size size : sizes) {
                size.removeThing(clothes);
//                sizeRepository.save(size);
            }
//            for (Color color : clothes.getColors()) {
//                color.removeThing(clothes);
//            }
            imageRepository.deleteAll(images);
            crudRepositoryHelper.delete(clothesRepository, id);
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
}
