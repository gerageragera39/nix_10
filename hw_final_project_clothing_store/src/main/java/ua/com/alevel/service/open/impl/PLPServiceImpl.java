package ua.com.alevel.service.open.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PLPServiceImpl implements PLPService {

    private final ClothesRepository clothesRepository;
    private final BrandRepository brandRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;
    private final CrudRepositoryHelper<Clothes, ClothesRepository> crudRepositoryHelper;


    public PLPServiceImpl(ClothesRepository clothesRepository, BrandRepository brandRepository, ColorRepository colorRepository, SizeRepository sizeRepository, CrudRepositoryHelper<Clothes, ClothesRepository> crudRepositoryHelper) {
        this.clothesRepository = clothesRepository;
        this.brandRepository = brandRepository;
        this.colorRepository = colorRepository;
        this.sizeRepository = sizeRepository;
        this.crudRepositoryHelper = crudRepositoryHelper;
    }

    @Override
    public DataTableResponse findAll(DataTableRequest dataTableRequest) {
        if (dataTableRequest.getRequestParamMap().size() == 0) {
            DataTableResponse<Clothes> dataTableResponse = crudRepositoryHelper.findAll(clothesRepository, dataTableRequest);
            return dataTableResponse;
        }
        return findClothesByRequest(dataTableRequest);
//        int page = dataTableRequest.getPage() - 1;
//        int size = dataTableRequest.getSize();
//        String sortBy = dataTableRequest.getSort();
//        String orderBy = dataTableRequest.getOrder();
//        Sort sort = orderBy.equals("desc")
//                ? Sort.by(sortBy).descending()
//                : Sort.by(sortBy).ascending();
//        PageRequest pageRequest = PageRequest.of(page, size, sort);
//
//        if (dataTableRequest.getRequestParamMap().get(WebUtil.BRAND_PARAM) != null &&
//                dataTableRequest.getRequestParamMap().get(WebUtil.SEARCH_CLOTHES_PARAM) != null) {
//            Long brandId = Long.parseLong(String.valueOf(dataTableRequest.getRequestParamMap().get(WebUtil.BRAND_PARAM)));
//            String clothesSearch = (String) dataTableRequest.getRequestParamMap().get(WebUtil.SEARCH_CLOTHES_PARAM);
//            List<Clothes> items = clothesRepository.findAllByBrandIdAndTitleContaining(brandId, clothesSearch, pageRequest);
//            DataTableResponse<Clothes> dataTableResponse = initDataTableResponse(dataTableRequest, items, sortBy, orderBy);
//            dataTableResponse.setItemsSize(clothesRepository.countAllByBrandIdAndTitleContaining(brandId, clothesSearch));
//            return dataTableResponse;
//        }
//
//        if (dataTableRequest.getRequestParamMap().get(WebUtil.BRAND_PARAM) != null) {
//            Long brandId = Long.parseLong(String.valueOf(dataTableRequest.getRequestParamMap().get(WebUtil.BRAND_PARAM)));
//            List<Clothes> items = clothesRepository.findAllByBrandId(brandId, pageRequest);
//            DataTableResponse<Clothes> dataTableResponse = initDataTableResponse(dataTableRequest, items, sortBy, orderBy);
//            dataTableResponse.setItemsSize(clothesRepository.countAllByBrandId(brandId));
//            return dataTableResponse;
//        }
//
//        if (dataTableRequest.getRequestParamMap().get(WebUtil.SEARCH_CLOTHES_PARAM) != null) {
//            String clothesSearch = (String) dataTableRequest.getRequestParamMap().get(WebUtil.SEARCH_CLOTHES_PARAM);
//            List<Clothes> items = clothesRepository.findAllByTitleContaining(clothesSearch, pageRequest);
//            DataTableResponse<Clothes> dataTableResponse = initDataTableResponse(dataTableRequest, items, sortBy, orderBy);
//            dataTableResponse.setItemsSize(clothesRepository.countAllByTitleContaining(clothesSearch));
//            return dataTableResponse;
//        }
//
//        if (dataTableRequest.getRequestParamMap().get(WebUtil.COLOR_PARAM) != null) {
//            Long colorId = Long.parseLong(String.valueOf(dataTableRequest.getRequestParamMap().get(WebUtil.COLOR_PARAM)));
//            List<Clothes> items = clothesRepository.findAllByColorsContains(colorRepository.findById(colorId).get(), pageRequest);
//            DataTableResponse<Clothes> dataTableResponse = initDataTableResponse(dataTableRequest, items, sortBy, orderBy);
//            dataTableResponse.setItemsSize(clothesRepository.countAllByColorsContains(colorRepository.findById(colorId).get()));
//            return dataTableResponse;
//        }
//
//        if (dataTableRequest.getRequestParamMap().get(WebUtil.SEX_PARAM) != null) {
//            int id = Integer.parseInt(String.valueOf(dataTableRequest.getRequestParamMap().get(WebUtil.SEX_PARAM)));
//            Sexes sex;
//            try {
//                sex = Sexes.values()[id];
//            } catch (ArrayIndexOutOfBoundsException e) {
//                throw new EntityNotFoundException("bad request");
//            }
//            List<Clothes> items = clothesRepository.findAllBySexEquals(sex, pageRequest);
//            DataTableResponse<Clothes> dataTableResponse = initDataTableResponse(dataTableRequest, items, sortBy, orderBy);
//            dataTableResponse.setItemsSize(clothesRepository.countAllBySexEquals(sex));
//            return dataTableResponse;
//        }
//        if (dataTableRequest.getRequestParamMap().get(WebUtil.TYPE_PARAM) != null) {
//            int id = Integer.parseInt(String.valueOf(dataTableRequest.getRequestParamMap().get(WebUtil.TYPE_PARAM)));
//            ThingTypes type;
//            try {
//                type = ThingTypes.values()[id];
//            } catch (ArrayIndexOutOfBoundsException e) {
//                throw new EntityNotFoundException("bad request");
//            }
//            List<Clothes> items = clothesRepository.findAllByTypeEquals(type, pageRequest);
//            DataTableResponse<Clothes> dataTableResponse = initDataTableResponse(dataTableRequest, items, sortBy, orderBy);
//            dataTableResponse.setItemsSize(clothesRepository.countAllByTypeEquals(type));
//            return dataTableResponse;
//        }
//
//        DataTableResponse<Clothes> dataTableResponse = crudRepositoryHelper.findAll(clothesRepository, dataTableRequest);
//        return dataTableResponse;
    }

    @Override
    public Clothes findById(Long id) {
        return crudRepositoryHelper.findById(clothesRepository, id).get();
    }

    @Override
    public Map<Long, String> findAllBrands() {
        Map<Long, String> map = new HashMap<>();
        List<Brand> brands = brandRepository.findAll();
        for (Brand brand : brands) {
            map.put(brand.getId(), brand.getName());
        }
        return map;
    }

    @Override
    public Map<Long, String> findAllColors() {
        Map<Long, String> map = new HashMap<>();
        List<Color> colors = colorRepository.findAll();
        for (Color color : colors) {
            map.put(color.getId(), color.getColorName());
        }
        return map;
    }

    @Override
    public Map<Long, String> findAllTypes() {
        Map<Long, String> map = new HashMap<>();
        for (int i = 0; i < ThingTypes.values().length; i++) {
            map.put((long) i, ThingTypes.values()[i].name());
        }
        return map;
    }

    @Override
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
//        dataTableResponse.setItemsSize(items.size());
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

        List<Clothes> clothes = new ArrayList<>();
        //must not be null
        String clothesSearch = null;
        if (dataTableRequest.getRequestParamMap().get(WebUtil.SEARCH_CLOTHES_PARAM) != null) {
            clothesSearch = (String) dataTableRequest.getRequestParamMap().get(WebUtil.SEARCH_CLOTHES_PARAM);
            clothes.addAll(clothesRepository.findAllByTitleContaining(clothesSearch, pageRequest));
        }

        Long brandId = null;
        if (dataTableRequest.getRequestParamMap().get(WebUtil.BRAND_PARAM) != null) {
            brandId = Long.parseLong(String.valueOf(dataTableRequest.getRequestParamMap().get(WebUtil.BRAND_PARAM)));
            clothes.addAll(clothesRepository.findAllByBrandId(brandId, pageRequest));
        }

        Long colorId = null;
        if (dataTableRequest.getRequestParamMap().get(WebUtil.COLOR_PARAM) != null) {
            colorId = Long.parseLong(String.valueOf(dataTableRequest.getRequestParamMap().get(WebUtil.COLOR_PARAM)));
            clothes.addAll(clothesRepository.findAllByColorsContains(colorRepository.findById(colorId).get(), pageRequest));
        }

        Long sizeId = null;
        if (dataTableRequest.getRequestParamMap().get(WebUtil.CLOTHES_SIZE_PARAM) != null) {
            sizeId = Long.parseLong(String.valueOf(dataTableRequest.getRequestParamMap().get(WebUtil.CLOTHES_SIZE_PARAM)));
            clothes.addAll(clothesRepository.findAllBySizesContains(sizeRepository.findById(sizeId).get(), pageRequest));
        }

        int sexId = 0;
        if (dataTableRequest.getRequestParamMap().get(WebUtil.SEX_PARAM) != null) {
            sexId = Integer.parseInt(String.valueOf(dataTableRequest.getRequestParamMap().get(WebUtil.SEX_PARAM)));
            Sexes sex;
            try {
                sex = Sexes.values()[sexId];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new EntityNotFoundException("bad request");
            }
            clothes.addAll(clothesRepository.findAllBySexEquals(sex, pageRequest));
        }

        int typeId = 0;
        if (dataTableRequest.getRequestParamMap().get(WebUtil.TYPE_PARAM) != null) {
            typeId = Integer.parseInt(String.valueOf(dataTableRequest.getRequestParamMap().get(WebUtil.TYPE_PARAM)));
            ThingTypes type;
            try {
                type = ThingTypes.values()[typeId];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new EntityNotFoundException("bad request");
            }
            clothes.addAll(clothesRepository.findAllByTypeEquals(type, pageRequest));
        }


        if (dataTableRequest.getRequestParamMap().get(WebUtil.SEARCH_CLOTHES_PARAM) != null) {
            List<Clothes> tempList = new ArrayList<>();
            for (Clothes thing : clothes) {
                if (thing.getTitle().toLowerCase().contains(clothesSearch)) {
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
                    }
                }
                if(uniq) {
                    items.add(thing);
                }
            }
        }

        DataTableResponse<Clothes> dataTableResponse = initDataTableResponse(dataTableRequest, items, sortBy, orderBy);
//        dataTableResponse.setItemsSize(clothesRepository.countAllByTypeEquals(type));
        dataTableResponse.setItemsSize(clothes.size());
        return dataTableResponse;
    }
}
