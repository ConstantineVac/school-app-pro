package grauebcf.schoolapppro.controller;

import grauebcf.schoolapppro.model.Specialty;
import grauebcf.schoolapppro.model.Teacher;
import grauebcf.schoolapppro.repository.SpecialtyRepository;
import grauebcf.schoolapppro.repository.TeacherRepository;
import grauebcf.schoolapppro.service.SpecialtyService;
import grauebcf.schoolapppro.service.TeacherService;
import grauebcf.schoolapppro.service.exception.TeacherNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class TeacherController {

    private final TeacherService teacherService;
    private final TeacherRepository teacherRepository;
    private final SpecialtyService specialtyService;
    private final SpecialtyRepository specialtyRepository;

    @Autowired
    public TeacherController(TeacherService teacherService, TeacherRepository teacherRepository, SpecialtyService specialtyService, SpecialtyRepository specialtyRepository) {
        this.teacherService = teacherService;
        this.teacherRepository = teacherRepository;
        this.specialtyService = specialtyService;
        this.specialtyRepository = specialtyRepository;
    }

    @GetMapping("/teacher")
    public ModelAndView showTeacherForm() {
        List<Specialty> specialties = specialtyService.getAllSpecialties(); 
        ModelAndView modelAndView = new ModelAndView("teacher");
        modelAndView.addObject("specialties", specialties);
        return modelAndView;
    }

    @PostMapping("/teacher/search")
    public ModelAndView searchTeachers(@RequestParam(value = "teacherLastName", required = false) String lastname) {
        List<Teacher> teachers;

        if (lastname != null && !lastname.trim().isEmpty()) {
            teachers = teacherService.searchTeachers(lastname);
        } else {
            teachers = teacherService.getAllTeachers();
        }

        ModelAndView modelAndView = new ModelAndView("teachers");
        modelAndView.addObject("teachers", teachers);
        return modelAndView;
    }


    @PostMapping("/teacher/insert")
    public String saveTeacher(
            @RequestParam("ssn") String ssn,
            @RequestParam("firstname") String firstname,
            @RequestParam("lastname") String lastname,
            @RequestParam("specialtyId") Long specialtyId,
            Model model) { 

        Teacher teacher = new Teacher();
        teacher.setSsn(ssn);
        teacher.setFirstname(firstname);
        teacher.setLastname(lastname);

        Specialty specialty = specialtyRepository.getSpecialtyById(specialtyId);
        teacher.setSpecialty(specialty);

        teacherService.insertTeacher(teacher);

        // Add teacher data to the model
        model.addAttribute("ssn", teacher.getSsn());
        model.addAttribute("firstname", teacher.getFirstname());
        model.addAttribute("lastname", teacher.getLastname());
        model.addAttribute("specialty", specialtyId);

        return "teacherInserted"; 
    }

    @GetMapping(value = "/editTeacher/{id}")
    public ModelAndView editTeacher(@PathVariable("id") String teacherId) {
        List<Specialty> specialties = specialtyService.getAllSpecialties(); 
        ModelAndView modelAndView = new ModelAndView("teacherUpdate");
        Long tId = Long.parseLong(teacherId);
        Teacher formTeacher = teacherRepository.getTeachersByTeacherId(tId);
        modelAndView.addObject("teacher", formTeacher);
        modelAndView.addObject("specialties", specialties);
        return modelAndView;
    }

    @PostMapping(value = "/teacher/updateTeacher/teacher/{id}")
    public String updateTeacher(@ModelAttribute("teacher") Teacher teacher,
                                @PathVariable("id") Long id,
                                @RequestParam("ssn") String ssn,
                                @RequestParam("firstname") String firstname,
                                @RequestParam("lastname") String lastname,
                                @RequestParam("specialtyId") Long specialtyId,
                                Model model) throws TeacherNotFoundException {
        // Retrieve teacher by ID.
        Teacher existingTeacher = teacherRepository.getTeachersByTeacherId(id);

        // Update found teacher properties
        existingTeacher.setFirstname(teacher.getFirstname());
        existingTeacher.setLastname(teacher.getLastname());
        existingTeacher.setSsn(teacher.getSsn());


        Specialty specialty = specialtyRepository.getSpecialtyById(specialtyId);
        existingTeacher.setSpecialty(specialty);

        teacherService.updateTeacher(existingTeacher);

        // Add teacher data to the model
        model.addAttribute("ssn", teacher.getSsn());
        model.addAttribute("firstname", teacher.getFirstname());
        model.addAttribute("lastname", teacher.getLastname());
        model.addAttribute("specialty", specialtyId);

        return "teacherUpdated";
    }

    @RequestMapping(value = "/teacher/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
    public ModelAndView deleteTeacher(@RequestParam("teacherId") Long teacherId) {
        Teacher deletedTeacher;
        Long specialtyId = null; // Initialize specialtyId to null

        try {
            deletedTeacher = teacherRepository.getTeachersByTeacherId(teacherId); // Retrieve the teacher before deletion

            // Store the specialtyId from the deleted teacher
            if (deletedTeacher != null && deletedTeacher.getSpecialty() != null) {
                specialtyId = specialtyRepository.getSpecialtyById(deletedTeacher.getSpecialty().getSpecialtyId()).getSpecialtyId();
            }
            teacherService.deleteTeacher(teacherId); // Delete the teacher
        } catch (TeacherNotFoundException e) {
            ModelAndView modelAndView = new ModelAndView("error");
            modelAndView.addObject("errorMessage", "Teacher not found");
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView("teacherDeleted");
        modelAndView.addObject("deletedTeacher", deletedTeacher);

        // Add the specialtyId to the modelAndView
        modelAndView.addObject("specialtyId", specialtyId);

        return modelAndView;
    }
}

