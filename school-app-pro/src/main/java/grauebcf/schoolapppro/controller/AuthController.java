package grauebcf.schoolapppro.controller;

import grauebcf.schoolapppro.authentication.CustomAuthenticationSuccessHandler;
import grauebcf.schoolapppro.dto.UserRegisterDTO;
import grauebcf.schoolapppro.model.User;
import grauebcf.schoolapppro.repository.UserRepository;
import grauebcf.schoolapppro.service.ISecurityService;
import grauebcf.schoolapppro.service.UserService;
import grauebcf.schoolapppro.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class AuthController {

    private final UserValidator userValidator;
    private final UserRepository userRepository;
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;
    private final ISecurityService securityService;
    private final AuthenticationManager authenticationManager;
    @Autowired
    public AuthController(UserRepository userRepository,
                          UserService userService,
                          PasswordEncoder passwordEncoder,
                          ISecurityService securityService,
                          AuthenticationManager authenticationManager,
                          UserValidator userValidator) {

        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.securityService = securityService;
        this.userValidator = userValidator;
        this.authenticationManager = authenticationManager;
    }


    @GetMapping(path = "/login")
    public String login(Principal principal, HttpServletRequest request, Model model) {
        String referer = request.getHeader("Referer");
        request.getSession().setAttribute(CustomAuthenticationSuccessHandler.REDIRECT_URL, referer);
        // Check if an error message should be displayed
        String errorMessage = (String) request.getSession().getAttribute("loginErrorMessage");
        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
            request.getSession().removeAttribute("loginErrorMessage"); // Remove the message from the session
        }

        return principal == null ? "login" : "redirect:/dashboard";
    }




//    @GetMapping(path = "/index")
//    public String root(Principal principal) {
//        return principal == null ? "login" : "redirect:/dashboard"; // Change to "/dashboard"
//    }



    @PostMapping("/authenticate")
    public String processRegistrationForm(@Valid @ModelAttribute("userRegisterDTO") UserRegisterDTO userRegisterDTO, BindingResult result) {
        userValidator.validate(userRegisterDTO, result);

        if (result.hasErrors()) {
            return "login";
        }

        //     Encode the user's password using BCrypt
        String rawPassword = userRegisterDTO.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        userRegisterDTO.setPassword(encodedPassword);

        User createdUser = userService.registerUser(userRegisterDTO);

        // login with the newly created account and redirect to search page
        securityService.autoLogin(createdUser);

        return "redirect:/dashboard";
    }


    @GetMapping("/authenticate")
    public String showRegistrationForm(Model model) {
        // Create a new UserRegisterDTO object and add it to the model
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        model.addAttribute("userRegisterDTO", userRegisterDTO);

        return "login"; // Assuming userLogin.html contains both login and registration forms
    }

}

//@Controller
//public class AuthController {
//
//    private final UserRepository userRepository;
//    private final UserService userService;
//
//    @Autowired
//    public AuthController(UserRepository userRepository, UserService userService) {
//        this.userRepository = userRepository;
//        this.userService = userService;
//    }
//
//    @GetMapping("/login")
//    public ModelAndView showLoginForm(@RequestParam(name = "error", required = false) String error,
//                                      @RequestParam(name = "registration", required = false) String registration) {
//        ModelAndView modelAndView = new ModelAndView("login");
//
//        // Add any error or registration success messages to the model
//        if (error != null) {
//            modelAndView.addObject("error", "Authentication failed. Please check your credentials.");
//        }
//        if (registration != null) {
//            modelAndView.addObject("registration", "Registration successful. You can now log in.");
//        }
//
//        return modelAndView;
//    }
//
//    @PostMapping("/login")
//    public String login(@RequestParam("email") String email,
//                        @RequestParam("password") String password) {
//        // Implement your authentication logic here using Spring Security
//        // You don't need to handle authentication here; Spring Security handles it
//
//        // Redirect to the dashboard on successful login
//        return "redirect:/dashboard";
//    }
//
//    @PostMapping("/authenticate")
//    public String register(@RequestParam("email") String email,
//                           @RequestParam("password") String password) {
//        // Implement your registration logic here using Spring Security
//        // You don't need to handle registration here; Spring Security handles it
//
//        // Redirect to the login page with a registration success message
//        return "redirect:/login?registration=success";
//    }


