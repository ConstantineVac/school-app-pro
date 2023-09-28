package grauebcf.schoolapppro.repository;

import grauebcf.schoolapppro.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findStudentByLastname(String lastname);

    @Query("SELECT s FROM Student s WHERE s.lastname LIKE %:lastName%")
    List<Student> getStudentsByLastname(@Param("lastName") String lastname); // Use List<Student> instead of Student

    @Query("SELECT s FROM Student s WHERE s.studentId = :studentId") // Updated query parameter syntax
    Student getStudentsByStudentId(@Param("studentId") Long studentId);

}

