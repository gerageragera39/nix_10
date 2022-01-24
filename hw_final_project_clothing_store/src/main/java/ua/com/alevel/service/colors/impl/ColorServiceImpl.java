package ua.com.alevel.service.colors.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.colors.Color;
import ua.com.alevel.persistence.repository.colors.ColorRepository;
import ua.com.alevel.service.colors.ColorService;

import java.util.List;
import java.util.Optional;

@Service
public class ColorServiceImpl implements ColorService {

    private final ColorRepository colorRepository;

    public ColorServiceImpl(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }


    @Override
    public List<Color> findAll() {
        return colorRepository.findAll();
    }

    @Override
    public Color findByName(String colorName) {
        Optional<Color> optionalColor = colorRepository.findColorByColorName(colorName);
        if(optionalColor.isPresent()) {
            return optionalColor.get();
        }
        return null;
    }

    @Override
    public void update(Color color) {
        colorRepository.save(color);
    }
}
