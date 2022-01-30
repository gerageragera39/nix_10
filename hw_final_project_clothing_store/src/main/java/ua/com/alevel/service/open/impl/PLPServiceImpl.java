package ua.com.alevel.service.open.impl;

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
            CriteriaBuilder.In<String> inClause = criteriaBuilder.in(from.get("colors"));
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
                throw new EntityNotFoundException("entity it not found");
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
                throw new EntityNotFoundException("entity it not found");
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
