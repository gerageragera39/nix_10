package ua.com.alevel.service.open.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.brands.Brand;
import ua.com.alevel.persistence.entity.clothes.Clothes;
import ua.com.alevel.persistence.entity.colors.Color;
import ua.com.alevel.persistence.entity.sizes.Size;
import ua.com.alevel.persistence.repository.brands.BrandRepository;
import ua.com.alevel.persistence.repository.clothes.ClothesRepository;
import ua.com.alevel.persistence.repository.colors.ColorRepository;
import ua.com.alevel.persistence.repository.sizes.SizeRepository;
import ua.com.alevel.persistence.sex.Sexes;
import ua.com.alevel.persistence.thing_type.ThingTypes;
import ua.com.alevel.service.open.PLPService;
import ua.com.alevel.util.WebUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.*;

@Service
public class PLPServiceImpl implements PLPService {

    private final ClothesRepository clothesRepository;
    private final BrandRepository brandRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;
    private final CrudRepositoryHelper<Clothes, ClothesRepository> crudRepositoryHelper;

    @PersistenceContext
    private EntityManager entityManager;

    public PLPServiceImpl(ClothesRepository clothesRepository, BrandRepository brandRepository, ColorRepository colorRepository, SizeRepository sizeRepository, CrudRepositoryHelper<Clothes, ClothesRepository> crudRepositoryHelper) {
        this.clothesRepository = clothesRepository;
        this.brandRepository = brandRepository;
        this.colorRepository = colorRepository;
        this.sizeRepository = sizeRepository;
        this.crudRepositoryHelper = crudRepositoryHelper;
    }

    @Override
    @Transactional(readOnly = true)
    public DataTableResponse findAll(DataTableRequest request) {
        if (request.getRequestParamMap().size() == 0) {
            DataTableResponse<Clothes> dataTableResponse = crudRepositoryHelper.findAll(clothesRepository, request);
            return dataTableResponse;
        }
        return search(request);
    }

    @Override
    public Clothes findById(Long id) {
        return crudRepositoryHelper.findById(clothesRepository, id).get();
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, String> findAllBrands() {
        Map<Long, String> map = new HashMap<>();
        List<Brand> brands = brandRepository.findAll();
        for (Brand brand : brands) {
            map.put(brand.getId(), brand.getName());
        }
        return map;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, String> findAllColors() {
        Map<Long, String> map = new HashMap<>();
        List<Color> colors = colorRepository.findAll();
        for (Color color : colors) {
            map.put(color.getId(), color.getColorName());
        }
        return map;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, String> findAllTypes() {
        Map<Long, String> map = new HashMap<>();
        for (int i = 0; i < ThingTypes.values().length; i++) {
            map.put((long) i, ThingTypes.values()[i].name());
        }
        return map;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, String> findAllSizes() {
        Map<Long, String> map = new HashMap<>();
        List<Size> sizes = sizeRepository.findAll();
        for (Size size : sizes) {
            map.put(size.getId(), size.getSizeName());
        }
        return map;
    }

    private DataTableResponse<Clothes> initDataTableResponse(DataTableRequest dataTableRequest, List<Clothes> items, String sortBy, String orderBy) {
        DataTableResponse<Clothes> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setItems(items);
        dataTableResponse.setOrder(orderBy);
        dataTableResponse.setSort(sortBy);
        dataTableResponse.setCurrentPage(dataTableRequest.getPage());
        dataTableResponse.setPageSize(dataTableRequest.getSize());
        return dataTableResponse;
    }

    private DataTableResponse<Clothes> findClothesByRequest(DataTableRequest dataTableRequest) {
        int page = dataTableRequest.getPage() - 1;
        int size = dataTableRequest.getSize();
        String sortBy = dataTableRequest.getSort();
        String orderBy = dataTableRequest.getOrder();
        Sort sort = orderBy.equals("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        int numOfRepeat = 0;
        List<Long> longs = new ArrayList<>();

        List<Clothes> clothes = new ArrayList<>();
        String clothesSearch = null;
        if (dataTableRequest.getRequestParamMap().get(WebUtil.SEARCH_CLOTHES_PARAM) != null) {
            clothesSearch = (String) dataTableRequest.getRequestParamMap().get(WebUtil.SEARCH_CLOTHES_PARAM);
            clothes.addAll(clothesRepository.findAllByTitleContainingAndVisibleTrue(clothesSearch.toLowerCase(), pageRequest));
            longs.addAll(clothesRepository.findAllClothesIdByTitleContainingAndVisibleTrue(clothesSearch.toLowerCase()));
            numOfRepeat++;
        }

        Long brandId = null;
        if (dataTableRequest.getRequestParamMap().get(WebUtil.BRAND_PARAM) != null) {
            brandId = Long.parseLong(String.valueOf(dataTableRequest.getRequestParamMap().get(WebUtil.BRAND_PARAM)));
            clothes.addAll(clothesRepository.findAllByBrandIdAndVisibleTrue(brandId, pageRequest));
            longs.addAll(clothesRepository.findAllClothesIdByBrandIdAndVisibleTrue(brandId));
            numOfRepeat++;
        }

        Long colorId = null;
        if (dataTableRequest.getRequestParamMap().get(WebUtil.COLOR_PARAM) != null) {
            colorId = Long.parseLong(String.valueOf(dataTableRequest.getRequestParamMap().get(WebUtil.COLOR_PARAM)));
//            clothes.addAll(clothesRepository.findAllByColorsContainsAndVisibleTrue(colorRepository.findById(colorId).get(), pageRequest));
            clothes.addAll(clothesRepository.findAllByColorsContainsAndVisibleTrue(colorRepository.findById(colorId).get(), pageRequest));
//            List<Clothes> clothesList = clothesRepository.findAllByColorsContainsAndVisibleTrue(colorRepository.findById(colorId).get());
//            for (Clothes thing : clothesList) {
//                longs.add(thing.getId());
//            }

            List<Clothes> clothesList = colorRepository.findById(colorId).get().getClothes().stream().toList();
            for (Clothes thing : clothesList) {
                longs.add(thing.getId());
            }
            numOfRepeat++;
        }

        Long sizeId = null;
        if (dataTableRequest.getRequestParamMap().get(WebUtil.CLOTHES_SIZE_PARAM) != null) {
            sizeId = Long.parseLong(String.valueOf(dataTableRequest.getRequestParamMap().get(WebUtil.CLOTHES_SIZE_PARAM)));
            clothes.addAll(clothesRepository.findAllBySizesContainsAndVisibleTrue(sizeRepository.findById(sizeId).get(), pageRequest));
//            List<Clothes> clothesList = clothesRepository.findAllBySizesContainsAndVisibleTrue(sizeRepository.findById(sizeId).get());

            List<Clothes> clothesList = sizeRepository.findById(sizeId).get().getThings().stream().toList();
            for (Clothes thing : clothesList) {
                longs.add(thing.getId());
            }
            numOfRepeat++;
        }

        int sexId = 0;
        if (dataTableRequest.getRequestParamMap().get(WebUtil.SEX_PARAM) != null) {
            sexId = Integer.parseInt(String.valueOf(dataTableRequest.getRequestParamMap().get(WebUtil.SEX_PARAM)));
            Sexes sex;
            try {
                sex = Sexes.values()[sexId];
                longs.addAll(clothesRepository.findAllClothesIdBySexEqualsAndVisibleTrue(sex));
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new EntityNotFoundException("bad request");
            }
            clothes.addAll(clothesRepository.findAllBySexEqualsAndVisibleTrue(sex, pageRequest));
            numOfRepeat++;
        }

        int typeId = 0;
        if (dataTableRequest.getRequestParamMap().get(WebUtil.TYPE_PARAM) != null) {
            typeId = Integer.parseInt(String.valueOf(dataTableRequest.getRequestParamMap().get(WebUtil.TYPE_PARAM)));
            ThingTypes type;
            try {
                type = ThingTypes.values()[typeId];
                longs.addAll(clothesRepository.findAllClothesIdByTypeEqualsAndVisibleTrue(type));
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new EntityNotFoundException("bad request");
            }
            clothes.addAll(clothesRepository.findAllByTypeEqualsAndVisibleTrue(type, pageRequest));
            numOfRepeat++;
        }

        if (dataTableRequest.getRequestParamMap().get(WebUtil.SEARCH_CLOTHES_PARAM) != null) {
            List<Clothes> tempList = new ArrayList<>();
            for (Clothes thing : clothes) {
                if (thing.getTitle().toLowerCase().contains(clothesSearch.toLowerCase())) {
                    tempList.add(thing);
                }
            }
            clothes = tempList;
        }

        if (dataTableRequest.getRequestParamMap().get(WebUtil.BRAND_PARAM) != null) {
            List<Clothes> tempList = new ArrayList<>();
            for (Clothes thing : clothes) {
                if (thing.getBrand().getId() == brandId) {
                    tempList.add(thing);
                }
            }
            clothes = tempList;
        }

        if (dataTableRequest.getRequestParamMap().get(WebUtil.COLOR_PARAM) != null) {
            List<Clothes> tempList = new ArrayList<>();
            for (Clothes thing : clothes) {
                List<Color> colors = thing.getColors().stream().toList();
                for (Color color : colors) {
                    if (color.getId() == colorId) {
                        tempList.add(thing);
                        break;
                    }
                }
            }
            clothes = tempList;
        }

        if (dataTableRequest.getRequestParamMap().get(WebUtil.CLOTHES_SIZE_PARAM) != null) {
            List<Clothes> tempList = new ArrayList<>();
            for (Clothes thing : clothes) {
                List<Size> sizes = thing.getSizes().stream().toList();
                for (Size clothes_size : sizes) {
                    if (clothes_size.getId() == sizeId) {
                        tempList.add(thing);
                        break;
                    }
                }
            }
            clothes = tempList;
        }

        if (dataTableRequest.getRequestParamMap().get(WebUtil.SEX_PARAM) != null) {
            List<Clothes> tempList = new ArrayList<>();
            for (Clothes thing : clothes) {
                if (thing.getSex().name().equals(Sexes.values()[sexId].name())) {
                    tempList.add(thing);
                }
            }
            clothes = tempList;
        }

        if (dataTableRequest.getRequestParamMap().get(WebUtil.TYPE_PARAM) != null) {
            List<Clothes> tempList = new ArrayList<>();
            for (Clothes thing : clothes) {
                if (thing.getType().name().equals(ThingTypes.values()[typeId].name())) {
                    tempList.add(thing);
                }
            }
            clothes = tempList;
        }

        List<Clothes> items = new ArrayList<>();
        if (clothes.size() != 0) {
            items.add(clothes.get(0));
            for (Clothes thing : clothes) {
                boolean uniq = true;
                for (Clothes item : items) {
                    if (thing.equals(item)) {
                        uniq = false;
                        break;
                    }
                }
                if (uniq) {
                    items.add(thing);
                }
            }
        }

        List<Long> correctIds = new ArrayList<>();
        for (int i = 0; i < longs.size(); i++) {
            int count = 0;
            for (int j = 0; j < longs.size(); j++) {
                if ((i != j) && (longs.get(i) == longs.get(j))) {
                    count++;
                }
            }
            if (count == numOfRepeat - 1) {
                correctIds.add(longs.get(i));
            }
        }

        List<Long> uniqIds = new ArrayList<>();
        if (correctIds.size() != 0) {
            uniqIds.add(correctIds.get(0));
            for (Long l : correctIds) {
                boolean uniq = true;
                for (Long item : uniqIds) {
                    if (l.equals(item)) {
                        uniq = false;
                        break;
                    }
                }
                if (uniq) {
                    uniqIds.add(l);
                }
            }
        }

        DataTableResponse<Clothes> dataTableResponse = initDataTableResponse(dataTableRequest, items, sortBy, orderBy);

        dataTableResponse.setItemsSize(uniqIds.size());
        return dataTableResponse;
    }


    private DataTableResponse<Clothes> search(DataTableRequest dataTableRequest) {
        List<Clothes> items;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Clothes> criteriaQuery = criteriaBuilder.createQuery(Clothes.class);
        Root<Clothes> from = criteriaQuery.from(Clothes.class);

        int page = (dataTableRequest.getPage() - 1) * dataTableRequest.getSize();
        int size = dataTableRequest.getSize();

        List<Predicate> predicates = new ArrayList<>();
        Predicate cond = criteriaBuilder.and(criteriaBuilder.equal(from.get("visible"), true));
        predicates.add(cond);
        if (dataTableRequest.getOrder().equals("desc")) {
            criteriaQuery.orderBy(criteriaBuilder.desc(from.get(dataTableRequest.getSort())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.asc(from.get(dataTableRequest.getSort())));
        }

        int numOfRepeat = 0;
        List<Long> longs = new ArrayList<>();

        List<Clothes> clothes = new ArrayList<>();
        String clothesSearch = null;
        if (dataTableRequest.getRequestParamMap().get(WebUtil.SEARCH_CLOTHES_PARAM) != null) {
            clothesSearch = (String) dataTableRequest.getRequestParamMap().get(WebUtil.SEARCH_CLOTHES_PARAM);
            longs.addAll(clothesRepository.findAllClothesIdByTitleContainingAndVisibleTrue(clothesSearch.toLowerCase()));
            Predicate pred = criteriaBuilder.and(criteriaBuilder.like(from.get("title"), "%" + clothesSearch.toLowerCase() + "%"));

            predicates.add(pred);
            numOfRepeat++;
        }

        Long brandId = null;
        if (dataTableRequest.getRequestParamMap().get(WebUtil.BRAND_PARAM) != null) {
            brandId = Long.parseLong(String.valueOf(dataTableRequest.getRequestParamMap().get(WebUtil.BRAND_PARAM)));
            Predicate pred = criteriaBuilder.and(criteriaBuilder.equal(from.get("brand"),
                    Long.parseLong(String.valueOf(dataTableRequest.getRequestParamMap().get(WebUtil.BRAND_PARAM)))));

            predicates.add(pred);
            longs.addAll(clothesRepository.findAllClothesIdByBrandIdAndVisibleTrue(brandId));
            numOfRepeat++;
        }

        Long colorId = null;
        if (dataTableRequest.getRequestParamMap().get(WebUtil.COLOR_PARAM) != null) {
            colorId = Long.parseLong(String.valueOf(dataTableRequest.getRequestParamMap().get(WebUtil.COLOR_PARAM)));
//            clothes.addAll(clothesRepository.findAllByColorsContainsAndVisibleTrue(colorRepository.findById(colorId).get(), pageRequest));

            CriteriaBuilder.In<String> inClause = criteriaBuilder.in(from.get("colors"));

//            Predicate pred = criteriaBuilder.and(criteriaBuilder.in(from.get("colors").get(colorRepository.findById(colorId).get())));

            Expression<Collection<Color>> colors = from.get("colors");
            Predicate pred = criteriaBuilder.isMember(colorRepository.findById(colorId).get(), colors);
            predicates.add(pred);

            List<Clothes> clothesList = colorRepository.findById(colorId).get().getClothes().stream().toList();
            for (Clothes thing : clothesList) {
                longs.add(thing.getId());
            }
            numOfRepeat++;
        }

        Long sizeId = null;
        if (dataTableRequest.getRequestParamMap().get(WebUtil.CLOTHES_SIZE_PARAM) != null) {
            sizeId = Long.parseLong(String.valueOf(dataTableRequest.getRequestParamMap().get(WebUtil.CLOTHES_SIZE_PARAM)));

            Expression<Collection<Size>> sizes = from.get("sizes");
            Predicate pred = criteriaBuilder.isMember(sizeRepository.findById(sizeId).get(), sizes);
            predicates.add(pred);

            List<Clothes> clothesList = sizeRepository.findById(sizeId).get().getThings().stream().toList();
            for (Clothes thing : clothesList) {
                longs.add(thing.getId());
            }
            numOfRepeat++;
        }

        int sexId = 0;
        if (dataTableRequest.getRequestParamMap().get(WebUtil.SEX_PARAM) != null) {
            sexId = Integer.parseInt(String.valueOf(dataTableRequest.getRequestParamMap().get(WebUtil.SEX_PARAM)));
            Sexes sex;
            try {
                sex = Sexes.values()[sexId];
                longs.addAll(clothesRepository.findAllClothesIdBySexEqualsAndVisibleTrue(sex));
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new EntityNotFoundException("bad request");
            }
            Predicate pred = criteriaBuilder.and(criteriaBuilder.equal(from.get("sex"), sex));
            predicates.add(pred);
            numOfRepeat++;
        }

        int typeId = 0;
        if (dataTableRequest.getRequestParamMap().get(WebUtil.TYPE_PARAM) != null) {
            typeId = Integer.parseInt(String.valueOf(dataTableRequest.getRequestParamMap().get(WebUtil.TYPE_PARAM)));
            ThingTypes type;
            try {
                type = ThingTypes.values()[typeId];
                longs.addAll(clothesRepository.findAllClothesIdByTypeEqualsAndVisibleTrue(type));
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new EntityNotFoundException("bad request");
            }
            Predicate pred = criteriaBuilder.and(criteriaBuilder.equal(from.get("type"), type));
            predicates.add(pred);
            numOfRepeat++;
        }

        List<Long> correctIds = new ArrayList<>();
        for (int i = 0; i < longs.size(); i++) {
            int count = 0;
            for (int j = 0; j < longs.size(); j++) {
                if ((i != j) && (longs.get(i) == longs.get(j))) {
                    count++;
                }
            }
            if (count == numOfRepeat - 1) {
                correctIds.add(longs.get(i));
            }
        }

        List<Long> uniqIds = new ArrayList<>();
        if (correctIds.size() != 0) {
            uniqIds.add(correctIds.get(0));
            for (Long l : correctIds) {
                boolean uniq = true;
                for (Long item : uniqIds) {
                    if (l.equals(item)) {
                        uniq = false;
                        break;
                    }
                }
                if (uniq) {
                    uniqIds.add(l);
                }
            }
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        items = entityManager.createQuery(criteriaQuery)
                .setFirstResult(page)
                .setMaxResults(size)
                .getResultList();
        DataTableResponse<Clothes> response = new DataTableResponse<>();
        response.setSort(dataTableRequest.getSort());
        response.setOrder(dataTableRequest.getOrder());
        response.setCurrentPage(dataTableRequest.getPage());
        response.setPageSize(dataTableRequest.getSize());
        response.setItems(items);
        response.setItemsSize(uniqIds.size());
        return response;
    }
}
