package ua.com.alevel.service.sizes;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.entity.colors.Color;
import ua.com.alevel.persistence.entity.sizes.Size;

import java.util.List;

public interface SizeService {

    List<Size> findAll();

    Size findByName(String color);

    void update(Size color);
}
