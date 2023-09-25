//package grauebcf.schoolapppro.controller;
//
//import grauebcf.schoolapppro.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@RequestMapping("/login")
//public class LoginController {
//
//    @Autowired
//    private UserService userService; // You should inject your UserService here
//
//    @GetMapping("/sign-in")
//    public String showLoginForm() {
//        return "login"; // Assuming "login.html" is in your templates directory
//    }
//    @PostMapping
//    public String loginUser(String email, String password, Model model) {
//        // Implement login logic using your UserService
//        // Redirect to a dashboard or home page after successful login
//        // Show error messages if login fails
//        return "redirect:/dashboard"; // Redirect to the dashboard page after successful login
//    }
//}
