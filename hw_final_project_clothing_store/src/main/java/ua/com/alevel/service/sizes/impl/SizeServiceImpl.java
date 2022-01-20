package ua.com.alevel.service.sizes.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.entity.colors.Color;
import ua.com.alevel.persistence.entity.sizes.Size;
import ua.com.alevel.persistence.repository.sizes.SizeRepository;
import ua.com.alevel.service.sizes.SizeService;

import java.util.List;
import java.util.Optional;

@Service
public class SizeServiceImpl implements SizeService {

    private final SizeRepository sizeRepository;

    public SizeServiceImpl(SizeRepository sizeRepository) {
        this.sizeRepository = sizeRepository;
    }

    @Override
    public List<Size> findAll() {
        return sizeRepository.findAll();
    }

    @Override
    public Size findByName(String sizeName) {
        Optional<Size> optionalSize = sizeRepository.findSizeBySizeName(sizeName);
        if(optionalSize.isPresent()) {
            return optionalSize.get();
        }
        return null;
    }

    @Override
    public void update(Size size) {
        sizeRepository.save(size);
    }
}
