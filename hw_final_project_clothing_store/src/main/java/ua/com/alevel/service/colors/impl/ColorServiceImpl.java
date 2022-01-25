package ua.com.alevel.service.colors.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.persistence.crud.CrudRepositoryHelper;
import ua.com.alevel.persistence.entity.colors.Color;
import ua.com.alevel.persistence.repository.colors.ColorRepository;
import ua.com.alevel.service.colors.ColorService;

import java.util.List;
import java.util.Optional;

@Service
public class ColorServiceImpl implements ColorService {

    private final ColorRepository colorRepository;
    private final CrudRepositoryHelper<Color, ColorRepository> crudRepositoryHelper;

    public ColorServiceImpl(ColorRepository colorRepository, CrudRepositoryHelper<Color, ColorRepository> crudRepositoryHelper) {
        this.colorRepository = colorRepository;
        this.crudRepositoryHelper = crudRepositoryHelper;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Color> findAll() {
        return colorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Color findByName(String colorName) {
        Optional<Color> optionalColor = colorRepository.findColorByColorName(colorName);
        if (optionalColor.isPresent()) {
            return optionalColor.get();
        }
        return null;
    }

    @Override
    public void update(Color color) {
        crudRepositoryHelper.update(colorRepository, color);
    }
}
