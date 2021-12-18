package ua.com.alevel.persistence.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.alevel.persistence.entity.Countries;
import ua.com.alevel.persistence.entity.Population;

import java.util.List;

@Repository
public interface CountriesRepository extends BaseRepository<Countries> {

    Countries findByNameOfCountry(String nameOfCounty);

    @Query(value = "select c.nameOfCountry from Countries c")
    List<String> findAllCountriesNames();

    @Query(value = "select c.people.size from Countries c where c.id = :id")
    int findCount(@Param("id") Long id);

    @Query(value = "select c.people from Countries c where c.id = :id")
    List<Population> findPeopleByCountryId(Long id);

    List<Countries> findByNameOfCountryOrISO(String nameOfCountry, int ISO);

}
