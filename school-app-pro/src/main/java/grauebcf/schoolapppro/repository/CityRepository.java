package grauebcf.schoolapppro.repository;

import grauebcf.schoolapppro.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {

    @Query("SELECT c FROM City c WHERE c.cityName LIKE %:cityName%")
    List<City> findByCityName(String cityName);

    @Query("SELECT c FROM City c WHERE c.cityId = ?1")
    City getCityById(Long cityId);
}