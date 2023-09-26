package grauebcf.schoolapppro.service;

import grauebcf.schoolapppro.dto.CityUpdateDTO;
import grauebcf.schoolapppro.model.City;
import grauebcf.schoolapppro.service.exception.CityNotFoundException;

import java.util.List;

public interface ICityService {
    List<City> getAllCities();
    List<City> searchCities(String cityName);
    City insertCity(String cityName);
    City updateCity(City city) throws CityNotFoundException;
    void deleteCity(Long cityId) throws CityNotFoundException;
}
