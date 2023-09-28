package grauebcf.schoolapppro.service;

import grauebcf.schoolapppro.model.Student;
import grauebcf.schoolapppro.service.exception.StudentNotFoundException;

import java.util.List;

public interface IStudentService {
    List<Student> getAllStudents();
    List<Student> searchStudents(String lastname);
    Student insertStudent(Student student);
    Student updateStudent(Student student) throws StudentNotFoundException;
    void deleteStudent(Long studentId) throws StudentNotFoundException;

}
