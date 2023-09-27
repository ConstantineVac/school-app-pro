package grauebcf.schoolapppro.service;

import grauebcf.schoolapppro.model.Specialty;
import grauebcf.schoolapppro.repository.SpecialtyRepository;
import grauebcf.schoolapppro.service.exception.SpecialtyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SpecialtyService implements ISpecialtyService {

    private final SpecialtyRepository specialtyRepository;

    @Autowired
    public SpecialtyService(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    @Override
    public List<Specialty> getAllSpecialties() {
        return specialtyRepository.findAll();
    }

    @Override
    public List<Specialty> searchSpecialties(String specialtyName) {
        return specialtyRepository.findBySpecialtyName(specialtyName);
    }

    @Override
    public Specialty insertSpecialty(String specialtyName) {
        Specialty specialty = new Specialty();
        specialty.setSpecialtyName(specialtyName);
        return specialtyRepository.save(specialty);
    }

    @Override
    public Specialty updateSpecialty(Specialty specialty) throws SpecialtyNotFoundException {
        return specialtyRepository.save(specialty);
    }

    @Override
    public void deleteSpecialty(Long specialtyId) throws SpecialtyNotFoundException {
        // Check if specialty exists.
        if (!specialtyRepository.existsById(specialtyId)) {
            throw new SpecialtyNotFoundException(specialtyId);
        }

        // Delete the specialty by ID.
        specialtyRepository.deleteById(specialtyId);
    }
}
