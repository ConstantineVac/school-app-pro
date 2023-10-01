package grauebcf.schoolapppro.repository;

import grauebcf.schoolapppro.model.City;
import grauebcf.schoolapppro.model.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {

    @Query("SELECT s FROM Specialty s WHERE s.specialtyName LIKE %:specialtyName%")
    List<Specialty> findBySpecialtyName(String specialtyName);

    @Query("SELECT s FROM Specialty s WHERE s.specialtyId = ?1")
    Specialty getSpecialtyById(Long specialtyId);
}
