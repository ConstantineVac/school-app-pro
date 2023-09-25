//package grauebcf.schoolapppro.controller;
//
//import grauebcf.schoolapppro.dto.UserRegisterDTO;
//import grauebcf.schoolapppro.model.User;
//import grauebcf.schoolapppro.service.ISecurityService;
//import grauebcf.schoolapppro.service.IUserService;
//import grauebcf.schoolapppro.service.UserService;
//import grauebcf.schoolapppro.validator.UserValidator;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.validation.Valid;
//
//@Controller
//@RequestMapping("/register")
//public class RegisterController {
//
//    @Autowired
//    private UserService userService; // You should inject your UserService here
//
//    @GetMapping("login")
//    public String showRegistrationForm() {
//        return "login"; // Create an HTML template for the registration form
//    }
//
//    @PostMapping("/sign-up")
//    public String registerUser(UserRegisterDTO userDTO, Model model) {
//        // Implement registration logic using your UserService
//        // Redirect to a success page or show error messages as needed
//        return "redirect:/login"; // Redirect to the login page after successful registration
//    }
//}