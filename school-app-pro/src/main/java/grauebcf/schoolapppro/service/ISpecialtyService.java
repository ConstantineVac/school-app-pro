package grauebcf.schoolapppro.service;

import grauebcf.schoolapppro.model.Specialty;
import grauebcf.schoolapppro.service.exception.SpecialtyNotFoundException;

import java.util.List;

public interface ISpecialtyService {

    List<Specialty> getAllSpecialties();
    List<Specialty> searchSpecialties(String specialtyName);
    Specialty insertSpecialty(String specialtyName);
    Specialty updateSpecialty(Specialty specialty) throws SpecialtyNotFoundException;
    void deleteSpecialty(Long specialtyId) throws SpecialtyNotFoundException;
}
