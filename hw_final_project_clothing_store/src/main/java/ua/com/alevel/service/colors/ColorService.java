package ua.com.alevel.service.colors;

import ua.com.alevel.persistence.entity.colors.Color;
import ua.com.alevel.service.BaseService;

import java.util.List;

public interface ColorService {
    
    List<Color> findAll();

    Color findByName(String color);

    void update(Color color);
}
