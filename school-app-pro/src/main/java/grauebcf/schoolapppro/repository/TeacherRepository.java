package grauebcf.schoolapppro.repository;

import grauebcf.schoolapppro.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
