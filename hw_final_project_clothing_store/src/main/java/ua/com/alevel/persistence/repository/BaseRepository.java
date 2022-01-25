package ua.com.alevel.persistence.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import ua.com.alevel.persistence.entity.BaseEntity;

import java.util.List;

@NoRepositoryBean
public interface BaseRepository<E extends BaseEntity> extends JpaRepository<E, Long>, JpaSpecificationExecutor<E> {

    int countAllByVisibleTrue();

    List<E> findAllByVisibleTrue(Pageable pageable);

    List<E> findAllByVisible(boolean visible, Pageable pageable);
}

