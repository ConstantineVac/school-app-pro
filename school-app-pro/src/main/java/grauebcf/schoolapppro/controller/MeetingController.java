package grauebcf.schoolapppro.controller;

import grauebcf.schoolapppro.model.City;
import grauebcf.schoolapppro.model.Meeting;
import grauebcf.schoolapppro.model.Student;
import grauebcf.schoolapppro.model.Teacher;
import grauebcf.schoolapppro.repository.MeetingRepository;
import grauebcf.schoolapppro.repository.StudentRepository;
import grauebcf.schoolapppro.repository.TeacherRepository;
import grauebcf.schoolapppro.service.MeetingService;
import grauebcf.schoolapppro.service.StudentService;
import grauebcf.schoolapppro.service.TeacherService;
import grauebcf.schoolapppro.service.exception.MeetingNotFoundException;
import grauebcf.schoolapppro.service.exception.StudentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

@Controller
public class MeetingController {

    private final MeetingRepository meetingRepository;
    private final MeetingService meetingService;
    private final TeacherRepository teacherRepository;
    private final TeacherService teacherService;
    private final StudentRepository studentRepository;
    private final StudentService studentService;

    @Autowired

    public MeetingController(MeetingRepository meetingRepository,
                             MeetingService meetingService,
                             TeacherRepository teacherRepository,
                             TeacherService teacherService,
                             StudentRepository studentRepository,
                             StudentService studentService) {

        this.meetingRepository = meetingRepository;
        this.meetingService = meetingService;
        this.teacherRepository = teacherRepository;
        this.teacherService = teacherService;
        this.studentRepository = studentRepository;
        this.studentService = studentService;
    }

    @GetMapping("/meeting")
    public ModelAndView showMeetingForm() {
        List<Teacher> teachers = teacherService.getAllTeachers();
        List<Student> students = studentService.getAllStudents();
        ModelAndView modelAndView = new ModelAndView("meeting");
        modelAndView.addObject("teachers", teachers);
        modelAndView.addObject("students", students);
        return modelAndView;
    }

    @PostMapping("/meeting/search")
    public ModelAndView searchMeetings(@RequestParam(value = "meetingClassroom", required = false) String classroom) {
        List<Meeting> meetings;

        if (classroom != null && !classroom.trim().isEmpty()) {
            meetings = meetingService.searchMeetings(classroom);
        } else {
            meetings = meetingService.getAllMeetings();
        }

        ModelAndView modelAndView = new ModelAndView("meetings");
        modelAndView.addObject("meetings", meetings);
        return modelAndView;
    }

    @PostMapping("/meeting/insert")
    public String saveMeeting(
            @RequestParam("meetingDate") String meetingDate,
            @RequestParam("classroom") String classroom,
            @RequestParam("teacherId") Long teacherId,
            @RequestParam("studentId") Long studentId,
            Model model) {


        Meeting meeting = new Meeting();
        meeting.setMeetingDate(LocalDate.parse(meetingDate));
        meeting.setClassroom(classroom);

        Teacher teacher = teacherRepository.getTeachersByTeacherId(teacherId);
        Student student = studentRepository.getStudentsByStudentId(studentId);
        meeting.setTeacher(teacher);
        meeting.setStudent(student);

        // save the new meeting.
        meetingService.insertMeeting(meeting);

        // Add meeting data to the model.
        model.addAttribute("meetingDate", meeting.getMeetingDate());
        model.addAttribute("classroom", meeting.getClassroom());
        model.addAttribute("teacher", teacherId);
        model.addAttribute("student", studentId);

        return "meetingInserted"; // Redirect to the page confirmation for the meeting.
    }

    @GetMapping(value = "/editMeeting/{id}")
    public ModelAndView editMeeting(@PathVariable("id") String meetingId) {
        List<Teacher> teachers = teacherService.getAllTeachers();
        List<Student> students = studentService.getAllStudents();
        ModelAndView modelAndView = new ModelAndView("meetingUpdate");
        Long mId = Long.parseLong(meetingId);
        Meeting formMeeting = meetingRepository.getMeetingByMeetingId(mId);
        modelAndView.addObject("meeting", formMeeting);
        modelAndView.addObject("teachers", teachers);
        modelAndView.addObject("students", students);
        return modelAndView;
    }

    @PostMapping("/meeting/updateMeeting/meeting/{id}")
    public String updateMeeting(
            @PathVariable("id") Long id,
            @RequestParam("meetingDate") String meetingDate,
            @RequestParam("classroom") String classroom,
            @RequestParam("teacherId") Long teacherId,
            @RequestParam("studentId") Long studentId,
            Model model) throws MeetingNotFoundException {

        // Retrieve the existing student by ID.
        Meeting existingMeeting = meetingRepository.getMeetingByMeetingId(id);

        // Update the student properties.
        existingMeeting.setMeetingDate(LocalDate.parse(meetingDate));
        existingMeeting.setClassroom(classroom);

        Teacher teacher = teacherRepository.getTeachersByTeacherId(teacherId);
        Student student = studentRepository.getStudentsByStudentId(studentId);
        existingMeeting.setTeacher(teacher);
        existingMeeting.setStudent(student);



        // Update the student in the database.
        try {
            meetingService.updateMeeting(existingMeeting);
        } catch (MeetingNotFoundException e) {
            // Handle the exception if the student is not found.
            // You can redirect to an error page or handle it as needed.

            // For example:
            model.addAttribute("errorMessage", "Meeting not found");
            return "errorPage"; // Redirect to an error page
        }

        // Add updated student data to the model
        model.addAttribute("meetingDate", existingMeeting.getMeetingDate());
        model.addAttribute("classroom", existingMeeting.getClassroom());
        model.addAttribute("teacher", teacherId);
        model.addAttribute("student", studentId);

        return "meetingUpdated"; // Redirect to a page after updating the student
    }

    @RequestMapping(value = "/meeting/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
    public ModelAndView deleteMeeting(@RequestParam("meetingId") Long meetingId) {
        Meeting deletedMeeting;
        Long teacherId = null;
        Long studentId = null;

        try {
            deletedMeeting = meetingRepository.getMeetingByMeetingId(meetingId); // Retrieve the teacher before deletion

            // Store the teacherId from the deleted teacher
            if (deletedMeeting != null && deletedMeeting.getTeacher() != null) {
                teacherId = teacherRepository.getTeachersByTeacherId(deletedMeeting.getTeacher().getTeacherId()).getTeacherId();
            }

            // Store the teacherId from the deleted teacher
            if (deletedMeeting != null && deletedMeeting.getStudent() != null) {
                studentId = studentRepository.getStudentsByStudentId(deletedMeeting.getStudent().getStudentId()).getStudentId();
            }

            meetingService.deleteMeeting(meetingId); // Delete the student
        } catch (MeetingNotFoundException e) {
            ModelAndView modelAndView = new ModelAndView("error");
            modelAndView.addObject("errorMessage", "Meeting not found");
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView("meetingDeleted");
        modelAndView.addObject("deletedMeeting", deletedMeeting);

        // Add the specialtyId to the modelAndView
        modelAndView.addObject("teacherId", teacherId);
        modelAndView.addObject("studentId", studentId);

        return modelAndView;
    }
}
