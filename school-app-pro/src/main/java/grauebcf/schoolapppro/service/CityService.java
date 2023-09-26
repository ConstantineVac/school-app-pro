package grauebcf.schoolapppro.service;

import grauebcf.schoolapppro.dto.CityUpdateDTO;
import grauebcf.schoolapppro.model.City;
import grauebcf.schoolapppro.repository.CityRepository;
import grauebcf.schoolapppro.service.exception.CityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CityService implements ICityService{

    private final CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @Override
    public List<City> searchCities(String cityName) {
        return cityRepository.findByCityName(cityName);
    }

    @Override
    public City insertCity(String cityName) {
        City city = new City();
        city.setCityName(cityName);
        return cityRepository.save(city);
    }

//    @Override
//    public City updateCity(City city) throws CityNotFoundException {
//        // Retrieve the city by ID
//        city = cityRepository.findById(city.getCityId())
//                .orElseThrow(() -> new CityNotFoundException());
//
//        // Update the city name
//        city.setCityName(city.getCityName());
//
//        // Save the updated city
//        return city;
//    }


    @Override
    public City updateCity(City city) throws CityNotFoundException {
        return cityRepository.save(city);
    }

    @Override
    public void deleteCity(Long cityId) throws CityNotFoundException {
        // Check if the city exists
        if (!cityRepository.existsById(cityId)) {
            throw new CityNotFoundException(cityId);
        }

        // Delete the city by ID
        cityRepository.deleteById(cityId);
    }
}
