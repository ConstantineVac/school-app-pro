package grauebcf.schoolapppro.controller;

import grauebcf.schoolapppro.dto.CityUpdateDTO;
import grauebcf.schoolapppro.model.City;
import grauebcf.schoolapppro.repository.CityRepository;
import grauebcf.schoolapppro.service.CityService;
import grauebcf.schoolapppro.service.exception.CityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CityController {

    private final CityService cityService;

    private final CityRepository cityRepository;

    @Autowired
    public CityController(CityService cityService, CityRepository cityRepository) {
        this.cityService = cityService;
        this.cityRepository = cityRepository;
    }

    @GetMapping("/city")
    public ModelAndView showCityForm() {
        ModelAndView modelAndView = new ModelAndView("city");
        return modelAndView;
    }

    @PostMapping("/city/search")
    public ModelAndView searchCities(@RequestParam(value = "cityName", required = false) String cityName) {
        List<City> cities;

        if (cityName != null && !cityName.trim().isEmpty()) {
            cities = cityService.searchCities(cityName);
        } else {
            cities = cityService.getAllCities();
        }

        ModelAndView modelAndView = new ModelAndView("cities");
        modelAndView.addObject("cities", cities);
        return modelAndView;
    }


    @PostMapping("/city/insert")
    public ModelAndView insertCity(@RequestParam("cityName") String cityName) {
        City insertedCity = cityService.insertCity(cityName);
        ModelAndView modelAndView = new ModelAndView("cityInserted");
        modelAndView.addObject("cityName", insertedCity.getCityName());
        System.out.println("City inserted: " + insertedCity.getCityName());
        return modelAndView;
    }

    @GetMapping (value = "/editCity/{id}")
    public ModelAndView editCity(@PathVariable("id") String cityId) {
        ModelAndView modelAndView = new ModelAndView("cityUpdate");
        Long cId = Long.parseLong(cityId);
        City formCity = cityRepository.getCityById(cId);
        modelAndView.addObject("city", formCity); // Add cityId to the model
        return modelAndView;
    }


    @PostMapping(value = "/city/updateCity/city/{id}")
    public String updateCity(@ModelAttribute("city") City city, @PathVariable("id") Long id) throws CityNotFoundException {
        // Retrieve the existing city from the database by ID
        City existingCity = cityRepository.getCityById(id);

        // Update the properties of the existing city with the values from the submitted form
        existingCity.setCityName(city.getCityName());

        // Call your cityService method to update the city in the database
        cityService.updateCity(existingCity);

        // Redirect to a success page or wherever needed
        return "cityUpdated";
    }


    @RequestMapping(value = "/city/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
    public ModelAndView deleteCity(@RequestParam("cityId") Long cityId) {
        City deletedCity;
        try {
            deletedCity = cityRepository.getCityById(cityId); // Retrieve the city before deletion
            cityService.deleteCity(cityId); // Delete the city
        } catch (CityNotFoundException e) {
            ModelAndView modelAndView = new ModelAndView("error");
            modelAndView.addObject("errorMessage", "City not found");
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView("cityDeleted");
        modelAndView.addObject("deletedCity", deletedCity);
        return modelAndView;
    }
}

