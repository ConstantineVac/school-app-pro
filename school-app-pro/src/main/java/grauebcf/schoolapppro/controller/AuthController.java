package grauebcf.schoolapppro.controller;

import grauebcf.schoolapppro.dto.UserRegisterDTO;
import grauebcf.schoolapppro.model.User;
import grauebcf.schoolapppro.repository.UserRepository;
import grauebcf.schoolapppro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public AuthController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }


    @GetMapping("/login")
    public ModelAndView showLoginForm(@RequestParam(name = "error", required = false) String error,
                                      @RequestParam(name = "registration", required = false) String registration) {
        ModelAndView modelAndView = new ModelAndView("login"); // Assuming you have a login.html template

        // Add any error or registration success messages to the model
        if (error != null) {
            modelAndView.addObject("error", "Authentication failed. Please check your credentials.");
        }
        if (registration != null) {
            modelAndView.addObject("registration", "Registration successful. You can now log in.");
        }

        return modelAndView;
    }

    @PostMapping("/authenticate")
    public String authenticate(@RequestParam("formType") String formType,
                               @RequestParam(name = "email", required = false) String email,
                               @RequestParam(name = "password", required = false) String password) {
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);

        if ("login".equals(formType)) {
            // Implement your authentication logic here, such as checking the email and password
            // against a user database or using Spring Security

            // For example, if authentication is successful, you can set a boolean flag like this:
            boolean authenticated = authenticateUser(email, password);

            if (authenticated) {
                // Store user details in the session (you need to implement this part)
                // Example: SecurityContextHolder.getContext().setAuthentication(authentication);
                return "redirect:/dashboard"; // Redirect to the dashboard on successful login
            } else {
                // Handle authentication failure, e.g., display an error message
                return "redirect:/login?error=true";
            }
        } else if ("register".equals(formType)) {
            // Implement your registration logic here, such as saving user details
            // to a user database

            // For example, if registration is successful, you can set a boolean flag like this:
            boolean registered = registerUser(email, password);

            if (registered) {
                return "redirect:/login?registration=success";
            } else {
                // Handle registration failure, e.g., display an error message
                return "redirect:/login?registration=failed";
            }
        }
        return "redirect:/login"; // Default to login page
    }

    // Replace this with your actual authentication logic
    private boolean authenticateUser(String email, String password) {
        // Retrieve the user by email from the database
        User user = userRepository.getUserByEmail(email);

        // Check if the user exists and the provided password matches the stored password
        if (user != null && user.getPassword().equals(password)) {
            return true; // Authentication successful
        }

        return false; // Authentication failed
    }

    // Replace this with your actual registration logic
    private boolean registerUser(String email, String password) {
        // Check if the email is already registered
        if (userRepository.emailExists(email)) {
            return false; // Registration failed (email already exists)
        }

        // Create a new User object and save it to the database
        UserRegisterDTO newUser = new UserRegisterDTO(email, password);
        userService.registerUser(newUser);

        return true; // Registration successful
    }

}
