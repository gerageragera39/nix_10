package ua.com.alevel.service.clothes.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.clothes.Clothes;
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
        if (!clothesRepository.existsByCLG(entity.getCLG())) {
            crudRepositoryHelper.create(clothesRepository, entity);
        }
    }

    @Override
    public void update(Clothes entity) {
        crudRepositoryHelper.update(clothesRepository, entity);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void delete(Long id) {
        Optional<Clothes> optionalClothes = clothesRepository.findById(id);
        if (optionalClothes.isPresent()) {
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
    @Transactional(readOnly = true)
    public DataTableResponse<Clothes> findAll(DataTableRequest dataTableRequest) {
        int page = dataTableRequest.getPage() - 1;
        int size = dataTableRequest.getSize();
        String sortBy = dataTableRequest.getSort();
        String orderBy = dataTableRequest.getOrder();
        Sort sort = orderBy.equals("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        List<Clothes> items = clothesRepository.findAll(pageRequest).getContent();
        DataTableResponse<Clothes> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(items);
        dataTableResponse.setOrder(orderBy);
        dataTableResponse.setSort(sortBy);
        dataTableResponse.setCurrentPage(dataTableRequest.getPage());
        dataTableResponse.setPageSize(dataTableRequest.getSize());
        dataTableResponse.setItemsSize(clothesRepository.count());
        long count = clothesRepository.count();
        WebResponseUtil.initDataTableResponse(dataTableRequest, dataTableResponse, count);
        return dataTableResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, String> findColorsByThingId(Long id) {
        Map<Long, String> map = new HashMap<>();
        List<Color> colors = clothesRepository.findColorsByThingId(id);
        for (int i = 0; i < colors.size(); i++) {
            map.put(colors.get(i).getId(), colors.get(i).getColorName());
        }
        return map;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Color> findAllColors() {
        return colorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Clothes> findByClg(String clg) {
        return clothesRepository.findClothesByCLG(clg);
    }

    @Override
    public boolean existByClg(String clg) {
        return clothesRepository.existsByCLG(clg);
    }

    @Override
    public List<Clothes> findAll() {
        return clothesRepository.findAll();
    }
}
