package grauebcf.schoolapppro.service;

import grauebcf.schoolapppro.model.Student;
import grauebcf.schoolapppro.repository.StudentRepository;
import grauebcf.schoolapppro.service.exception.StudentNotFoundException;
import grauebcf.schoolapppro.service.exception.TeacherNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class StudentService implements IStudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public List<Student> searchStudents(String lastname) {
        return studentRepository.findStudentByLastname(lastname);
    }

    @Override
    public Student insertStudent(Student student) {
        if (student.getCity() != null) {
            student.getCity().addStudent(student);
        }
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Student student) throws StudentNotFoundException {
        if (!studentRepository.existsById(student.getStudentId())) {
            throw new StudentNotFoundException(student.getStudentId());
        }

        Student existingStudent = studentRepository.findById(student.getStudentId()).orElse(null);

        // Check if the city has changed
        if (existingStudent != null && existingStudent.getCity() != null && !existingStudent.getCity().equals(student.getCity())) {
            // If the student was previously associated with a different city, remove the student from that city's list of students
            existingStudent.getCity().removeStudent(existingStudent);
        }

        // If the student has a new city associated, add it using the convenience method
        if (student.getCity() != null) {
            student.getCity().addStudent(student);
        }

        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long studentId) throws StudentNotFoundException {
        if (!studentRepository.existsById(studentId)) {
            throw new StudentNotFoundException(studentId);
        }

        // Delete the teacher by ID.
        studentRepository.deleteById(studentId);
    }
}
