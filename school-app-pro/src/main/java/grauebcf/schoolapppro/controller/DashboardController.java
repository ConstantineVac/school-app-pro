package grauebcf.schoolapppro.controller;

import grauebcf.schoolapppro.service.CustomUserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(Principal principal, Model model) {
        if (principal != null) {
            String username = principal.getName(); // This gets the username (email) from UserDetails
            // Use username in your logic or add it to the model
            model.addAttribute("username", username);
        }
        return "dashboard";
    }
}
