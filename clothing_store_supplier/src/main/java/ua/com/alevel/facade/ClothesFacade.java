package ua.com.alevel.facade;

import ua.com.alevel.persistence.entity.Clothes;

import java.util.List;

public interface ClothesFacade {

    List<Clothes> findAll();

    void doNotNew(List<Clothes> clothes);
}
