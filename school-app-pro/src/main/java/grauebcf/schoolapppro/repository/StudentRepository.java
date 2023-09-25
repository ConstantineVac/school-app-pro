package grauebcf.schoolapppro.repository;

import grauebcf.schoolapppro.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
