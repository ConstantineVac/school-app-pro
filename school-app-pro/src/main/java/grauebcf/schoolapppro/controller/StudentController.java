package grauebcf.schoolapppro.controller;

import grauebcf.schoolapppro.model.City;
import grauebcf.schoolapppro.model.Student;
import grauebcf.schoolapppro.repository.CityRepository;
import grauebcf.schoolapppro.repository.StudentRepository;
import grauebcf.schoolapppro.service.CityService;
import grauebcf.schoolapppro.service.StudentService;
import grauebcf.schoolapppro.service.exception.StudentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

@Controller
public class StudentController {

    private final StudentRepository studentRepository;
    private final StudentService studentService;
    private final CityRepository cityRepository;
    private final CityService cityService;

    @Autowired
    public StudentController(StudentRepository studentRepository,
                             StudentService studentService,
                             CityRepository cityRepository,
                             CityService cityService) {

        this.studentRepository = studentRepository;
        this.studentService = studentService;
        this.cityRepository = cityRepository;
        this.cityService = cityService;
    }

    @GetMapping("/student")
    public ModelAndView showStudentForm() {
        List<City> cities = cityService.getAllCities(); // Replace with your service call
        ModelAndView modelAndView = new ModelAndView("student");
        modelAndView.addObject("cities", cities);
        return modelAndView;
    }

    @PostMapping("/student/search")
    public ModelAndView searchStudents(@RequestParam(value = "lastname", required = false) String lastname) {
        List<Student> students;

        if (lastname != null && !lastname.trim().isEmpty()) {
            students = studentService.searchStudents(lastname);
        } else {
            students = studentService.getAllStudents();
        }

        ModelAndView modelAndView = new ModelAndView("students");
        modelAndView.addObject("students", students);
        return modelAndView;
    }


    @PostMapping("/student/insert")
    public String saveTeacher(
            @RequestParam("firstname") String firstname,
            @RequestParam("lastname") String lastname,
            @RequestParam("cityId") Long cityId,
            @RequestParam("birthday") String birthday,
            Model model) { // Add Model as a method parameter

        // Assuming you have a service method to save the teacher
        Student student = new Student();
        student.setFirstname(firstname);
        student.setLastname(lastname);

        City city = cityRepository.getCityById(cityId);
        student.setCity(city);

        student.setBirthday(LocalDate.parse(birthday));

        studentService.insertStudent(student);

        // Add Student data to the model
        model.addAttribute("firstname", student.getFirstname());
        model.addAttribute("lastname", student.getLastname());
        model.addAttribute("city", cityId);
        model.addAttribute("birthday", student.getBirthday());

        return "studentInserted"; // Redirect to a page after saving the teacher
    }

    @GetMapping(value = "/editStudent/{id}")
    public ModelAndView editStudent(@PathVariable("id") String studentId) {
        List<City> cities = cityService.getAllCities(); // Replace with your service call
        ModelAndView modelAndView = new ModelAndView("studentUpdate");
        Long sId = Long.parseLong(studentId);
        Student formStudent = studentRepository.getStudentsByStudentId(sId);
        modelAndView.addObject("student", formStudent);
        modelAndView.addObject("cities", cities);
        return modelAndView;
    }

//    @PostMapping(value = "/student/updateStudent/student/{id}")
//    public String updateStudent(@ModelAttribute("student") Student student,
//                                @PathVariable("id") Long id,
//                                @RequestParam("firstname") String firstname,
//                                @RequestParam("lastname") String lastname,
//                                @RequestParam("cityId") Long cityId,
//                                @RequestParam("birthday") String birthday,
//                                Model model) throws StudentNotFoundException {
//        // Retrieve student by ID.
//        Student existingStudent = studentRepository.getStudentsByStudentId(id);
//
//        // Update found student properties
//        existingStudent.setFirstname(student.getFirstname());
//        existingStudent.setLastname(student.getLastname());
//
//        City city = cityRepository.getCityById(cityId);
//        existingStudent.setCity(city);
//
//        existingStudent.setBirthday(LocalDate.parse(birthday));
//
//        studentService.updateStudent(existingStudent);
//
//        // Add student data to the model
//        model.addAttribute("firstname", student.getFirstname());
//        model.addAttribute("lastname", student.getLastname());
//        model.addAttribute("city", cityId);
//        model.addAttribute("birthday", student.getBirthday());
//
//        return "studentUpdated";
//    }

    @PostMapping("/student/updateStudent/student/{id}")
    public String updateStudent(
            @PathVariable("id") Long id,
            @RequestParam("firstname") String firstname,
            @RequestParam("lastname") String lastname,
            @RequestParam("cityId") Long cityId,
            @RequestParam("birthday") String birthday,
            Model model) throws StudentNotFoundException {

        // Retrieve the existing student by ID.
        Student existingStudent = studentRepository.getStudentsByStudentId(id);

        // Update the student properties.
        existingStudent.setFirstname(firstname);
        existingStudent.setLastname(lastname);

        City city = cityRepository.getCityById(cityId);
        existingStudent.setCity(city);

        existingStudent.setBirthday(LocalDate.parse(birthday));

        // Update the student in the database.
        try {
            studentService.updateStudent(existingStudent);
        } catch (StudentNotFoundException e) {
            // Handle the exception if the student is not found.
            // You can redirect to an error page or handle it as needed.

            // For example:
            model.addAttribute("errorMessage", "Student not found");
            return "errorPage"; // Redirect to an error page
        }

        // Add updated student data to the model
        model.addAttribute("firstname", existingStudent.getFirstname());
        model.addAttribute("lastname", existingStudent.getLastname());
        model.addAttribute("city", cityId);
        model.addAttribute("birthday", existingStudent.getBirthday());

        return "studentUpdated"; // Redirect to a page after updating the student
    }



    @RequestMapping(value = "/student/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
    public ModelAndView deleteStudent(@RequestParam("studentId") Long studentId) {
        Student deletedStudent;
        Long cityId = null; // Initialize specialtyId to null

        try {
            deletedStudent = studentRepository.getStudentsByStudentId(studentId); // Retrieve the teacher before deletion

            // Store the specialtyId from the deleted teacher
            if (deletedStudent != null && deletedStudent.getCity() != null) {
                cityId = cityRepository.getCityById(deletedStudent.getCity().getCityId()).getCityId();
            }

           studentService.deleteStudent(studentId); // Delete the student
        } catch (StudentNotFoundException e) {
            ModelAndView modelAndView = new ModelAndView("error");
            modelAndView.addObject("errorMessage", "Student not found");
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView("studentDeleted");
        modelAndView.addObject("deletedStudent", deletedStudent);

        // Add the specialtyId to the modelAndView
        modelAndView.addObject("cityId", cityId);

        return modelAndView;
    }
}
