package grauebcf.schoolapppro.service;

import grauebcf.schoolapppro.model.Teacher;
import grauebcf.schoolapppro.repository.TeacherRepository;
import grauebcf.schoolapppro.service.exception.TeacherNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TeacherService implements ITeacherService{

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @Override
    public List<Teacher> searchTeachers(String lastname) {
        return teacherRepository.findTeachersByLastname(lastname);
    }

    @Override
    public Teacher insertTeacher(Teacher teacher) {
        if (teacher.getSpecialty() != null) {
            teacher.getSpecialty().addTeacher(teacher);
        }
        return teacherRepository.save(teacher); // assuming you have a teacherRepository.
    }

    @Override
    public Teacher updateTeacher(Teacher teacher) throws TeacherNotFoundException {
        if (!teacherRepository.existsById(teacher.getTeacherId())) {
            throw new TeacherNotFoundException(teacher.getTeacherId());
        }

        Teacher existingTeacher = teacherRepository.findById(teacher.getTeacherId()).orElse(null);

        // Check if the specialty has changed for the teacher
        if (existingTeacher != null && existingTeacher.getSpecialty() != null && !existingTeacher.getSpecialty().equals(teacher.getSpecialty())) {
            // If the teacher was previously associated with a different specialty, remove the teacher from that specialty's list
            existingTeacher.getSpecialty().removeTeacher(existingTeacher);
        }

        // If the teacher has a new specialty associated, add it using the convenience method
        if (teacher.getSpecialty() != null) {
            teacher.getSpecialty().addTeacher(teacher);
        }

        return teacherRepository.save(teacher);
    }

    @Override
    public void deleteTeacher(Long teacherId) throws TeacherNotFoundException {
        // Check if the teacher exists.
        if (!teacherRepository.existsById(teacherId)) {
            throw new TeacherNotFoundException(teacherId);
        }

        // Delete the teacher by ID.
        teacherRepository.deleteById(teacherId);
    }
}
