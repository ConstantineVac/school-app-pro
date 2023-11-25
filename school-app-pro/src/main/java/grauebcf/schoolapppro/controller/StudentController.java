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
    public ModelAndView searchStudents(@RequestParam(value = "studentLastName", required = false) String lastname) {
        System.out.println("Lastname parameter: " + lastname); // Debugging statement

        List<Student> students;

        if (lastname != null && !lastname.trim().isEmpty()) {
            students = studentService.searchStudents(lastname);
        } else {
            students = studentService.getAllStudents();
        }

        System.out.println("Number of students found: " + students.size()); // Debugging statement

        ModelAndView modelAndView = new ModelAndView("students");
        modelAndView.addObject("students", students);
        return modelAndView;
    }

    @PostMapping("/student/insert")
    public String saveStudent(
            @RequestParam("firstname") String firstname,
            @RequestParam("lastname") String lastname,
            @RequestParam("cityId") Long cityId,
            @RequestParam("birthday") String birthday,
            Model model) { // Add Model as a method parameter

        
        Student student = new Student();
        student.setFirstname(firstname);
        student.setLastname(lastname);

        City city = cityRepository.getCityById(cityId);
        student.setCity(city);

        student.setBirthday(LocalDate.parse(birthday));

        // save the new student.
        studentService.insertStudent(student);

        // Add Student data to the model
        model.addAttribute("firstname", student.getFirstname());
        model.addAttribute("lastname", student.getLastname());
        model.addAttribute("city", cityId);
        model.addAttribute("birthday", student.getBirthday());

        return "studentInserted"; 
    }

    @GetMapping(value = "/editStudent/{id}")
    public ModelAndView editStudent(@PathVariable("id") String studentId) {
        List<City> cities = cityService.getAllCities(); 
        ModelAndView modelAndView = new ModelAndView("studentUpdate");
        Long sId = Long.parseLong(studentId);
        Student formStudent = studentRepository.getStudentsByStudentId(sId);
        modelAndView.addObject("student", formStudent);
        modelAndView.addObject("cities", cities);
        return modelAndView;
    }

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
            model.addAttribute("errorMessage", "Student not found");
            return "errorPage"; // Redirect to an error page
        }

        // Add updated student data to the model
        model.addAttribute("firstname", existingStudent.getFirstname());
        model.addAttribute("lastname", existingStudent.getLastname());
        model.addAttribute("city", cityId);
        model.addAttribute("birthday", existingStudent.getBirthday());

        return "studentUpdated"; 
    }



    @RequestMapping(value = "/student/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
    public ModelAndView deleteStudent(@RequestParam("studentId") Long studentId) {
        Student deletedStudent;
        Long cityId = null; // Initialize cityId to null

        try {
            deletedStudent = studentRepository.getStudentsByStudentId(studentId); // Retrieve the teacher before deletion

            // Store the cityId from the deleted teacher
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
