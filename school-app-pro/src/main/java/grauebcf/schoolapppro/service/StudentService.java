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
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Student student) throws StudentNotFoundException {
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
