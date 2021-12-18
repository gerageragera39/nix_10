package ua.com.alevel.persistence.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.alevel.persistence.entity.Countries;
import ua.com.alevel.persistence.entity.Population;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PopulationRepository extends BaseRepository<Population> {

    Population findByPassportID(String passportID);

    @Query(value = "select p.countries from Population p")
    List<Countries> findAllAddedCountries();

    @Query(value = "select p.countries.size from Population p where p.id = :id")
    int findCount(@Param("id") Long id);

    @Query(value = "select p.countries from Population p where p.id = :id")
    List<Countries> findCountriesByPersonId(@Param("id") Long id);

}
