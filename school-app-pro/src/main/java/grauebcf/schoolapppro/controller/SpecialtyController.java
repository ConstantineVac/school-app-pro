package grauebcf.schoolapppro.controller;

import grauebcf.schoolapppro.model.Specialty;
import grauebcf.schoolapppro.repository.SpecialtyRepository;
import grauebcf.schoolapppro.service.SpecialtyService;
import grauebcf.schoolapppro.service.exception.SpecialtyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class SpecialtyController {

    private final SpecialtyService specialtyService;
    private final SpecialtyRepository specialtyRepository;

    @Autowired
    public SpecialtyController(SpecialtyService specialtyService, SpecialtyRepository specialtyRepository) {
        this.specialtyService = specialtyService;
        this.specialtyRepository = specialtyRepository;
    }

    @GetMapping("/specialty")
    public ModelAndView showSpecialtyForm() {
        ModelAndView modelAndView = new ModelAndView("specialty");
        return modelAndView;
    }

    @PostMapping("/specialty/search")
    public ModelAndView searchCities(@RequestParam(value = "specialtyName", required = false) String specialtyName) {
        List<Specialty> specialties;

        if (specialtyName != null && !specialtyName.trim().isEmpty()) {
            specialties = specialtyService.searchSpecialties(specialtyName);
        } else {
            specialties = specialtyService.getAllSpecialties();
        }

        ModelAndView modelAndView = new ModelAndView("specialties");
        modelAndView.addObject("specialties", specialties);
        return modelAndView;
    }

    @PostMapping("/specialty/insert")
    public ModelAndView insertSpecialty(@RequestParam("specialtyName") String specialtyName) {
        Specialty insertedSpecialty = specialtyService.insertSpecialty(specialtyName);
        ModelAndView modelAndView = new ModelAndView("specialtyInserted");
        modelAndView.addObject("specialtyName", insertedSpecialty.getSpecialtyName());
        System.out.println("Specialty inserted: " + insertedSpecialty.getSpecialtyName());
        return modelAndView;
    }

    @GetMapping (value = "/editSpecialty/{id}")
    public ModelAndView editSpecialty(@PathVariable("id") String specialtyId) {
        ModelAndView modelAndView = new ModelAndView("specialtyUpdate");
        Long sId = Long.parseLong(specialtyId);
        Specialty formSpecialty = specialtyRepository.getSpecialtyById(sId);
        modelAndView.addObject("specialty", formSpecialty); 
        return modelAndView;
    }

    @PostMapping(value = "/specialty/updateSpecialty/specialty/{id}")
    public String updateSpecialty(@ModelAttribute("specialty") Specialty specialty, @PathVariable("id") Long id) throws SpecialtyNotFoundException {
        // Retrieve the existing city from the database by ID
        Specialty existingSpecialty = specialtyRepository.getSpecialtyById(id);

        // Update the properties of the existing city with the values from the submitted form
        existingSpecialty.setSpecialtyName(specialty.getSpecialtyName());

        // Call your cityService method to update the city in the database
        specialtyService.updateSpecialty(existingSpecialty);

        return "specialtyUpdated";
    }

    @RequestMapping(value = "/specialty/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
    public ModelAndView deleteSpecialty(@RequestParam("specialtyId") Long specialtyId) {
        Specialty deletedSpecialty;
        try {
            deletedSpecialty = specialtyRepository.getSpecialtyById(specialtyId); // Retrieve the specialty before deletion
            specialtyService.deleteSpecialty(specialtyId);  // Delete the specialty
        } catch (SpecialtyNotFoundException e) {
            ModelAndView modelAndView = new ModelAndView("error");
            modelAndView.addObject("errorMessage", "Specialty not found");
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView("specialtyDeleted");
        modelAndView.addObject("deletedSpecialty", deletedSpecialty);
        return modelAndView;
    }
}
