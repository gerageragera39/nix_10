package ua.com.alevel.persistence.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.alevel.persistence.entity.Countries;
import ua.com.alevel.persistence.entity.Population;

import java.util.List;

@Repository
public interface PopulationRepository extends BaseRepository<Population> {

    Population findByPassportID(String passportID);

    @Query(value = "select p.countries.size from Population p where p.id = :id")
    int findCount(@Param("id") Long id);

    @Query(value = "select p.countries from Population p where p.id = :id")
    List<Countries> findCountriesByPersonId(@Param("id") Long id);

    List<Population> findAllByVisible(boolean visible, Pageable pageable);

    @Query(value = "select p from Population p where p.visible = :visible order by p.countries.size desc ")
    List<Population> findAllByVisibleTrueOrderByCountriesSizeDesc(@Param("visible") boolean visible, Pageable pageable);

    @Query(value = "select p from Population p where p.visible = :visible order by p.countries.size asc ")
    List<Population> findAllByVisibleTrueOrderByCountriesSizeAsc(@Param("visible") boolean visible, Pageable pageable);

    @Query(value = "select count(p) from Population p where p.visible = :visible")
    int numOfAllPeople(@Param("visible") boolean visible);

    List<Population> findAllByPassportID(String passportId);
}
