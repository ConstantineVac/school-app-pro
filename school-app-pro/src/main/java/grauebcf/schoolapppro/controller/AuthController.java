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
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import javax.naming.AuthenticationException;
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
        Object authenticationException = request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        if (authenticationException != null) {
            String errorMessage = null;

            if (authenticationException instanceof org.springframework.security.authentication.BadCredentialsException) {
                errorMessage = "Invalid email or password.";
            } else if (authenticationException instanceof org.springframework.security.core.userdetails.UsernameNotFoundException) {
                errorMessage = "User with this email does not exist.";
            }

            // Remove the exception from the session after capturing the error message
            request.getSession().removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

            // Set the error message in the model if one was found
            if (errorMessage != null) {
                model.addAttribute("errorLoginMessage", errorMessage);
            }
        }

        return principal == null ? "login" : "redirect:/dashboard";
    }


    @PostMapping("/authenticate")
    public String processRegistrationForm(@Valid @ModelAttribute("userRegisterDTO") UserRegisterDTO userRegisterDTO, BindingResult result, Model model) {
        userValidator.validate(userRegisterDTO, result);


        if (userRepository.emailExists(userRegisterDTO.getEmail())) {
            model.addAttribute("errorRegisterMessage", "Email already exists. Please log in.");
            return "login";
        }

        if (result.hasErrors()) {
            // Check for password-related errors
            if (result.hasFieldErrors("password")) {
                model.addAttribute("errorRegisterMessage", "Invalid password. Password must meet the criteria. At least 8 Characters long, including 1 lowercase, 1 uppercase and 1 number");
            } else {
                // Other validation errors occurred
                model.addAttribute("errorRegisterMessage", "Invalid input. Please check your email or password.");
            }
            return "login";
        }

        // Encode the user's password using BCrypt
        String rawPassword = userRegisterDTO.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        userRegisterDTO.setPassword(encodedPassword);

        try {
            User createdUser = userService.registerUser(userRegisterDTO);

            // login with the newly created account and redirect to dashboard
            securityService.autoLogin(createdUser);

            return "redirect:/dashboard";
        } catch (Exception e) {
            // Handle other exceptions if needed
//            model.addAttribute("errorRegisterMessage", "An error occurred during registration.");
            return "login"; // Replace with the name of your combined view
        }
    }
}




