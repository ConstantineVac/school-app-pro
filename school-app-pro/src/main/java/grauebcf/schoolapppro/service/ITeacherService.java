package grauebcf.schoolapppro.service;

import grauebcf.schoolapppro.model.Teacher;
import grauebcf.schoolapppro.service.exception.TeacherNotFoundException;

import java.util.List;

public interface ITeacherService {
    List<Teacher> getAllTeachers();
    List<Teacher> searchTeachers(String lastname);
    Teacher insertTeacher(Teacher teacher);
    Teacher updateTeacher(Teacher teacher) throws TeacherNotFoundException;
    void deleteTeacher(Long teacherId) throws TeacherNotFoundException;
}
