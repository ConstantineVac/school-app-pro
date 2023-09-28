package grauebcf.schoolapppro.repository;

import grauebcf.schoolapppro.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    List<Teacher> findTeachersByLastname(String lastname);

    @Query("SELECT t FROM Teacher t WHERE t.lastname like %:lastName%")
    Teacher getTeachersByLastname(@Param("lastName") String lastName);

    @Query("SELECT t FROM Teacher t WHERE t.teacherId = ?1")
    Teacher getTeachersByTeacherId(Long teacherId);

}

