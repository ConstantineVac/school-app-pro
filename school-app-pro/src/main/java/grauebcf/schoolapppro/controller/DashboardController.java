package grauebcf.schoolapppro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.security.Principal;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(Principal principal, Model model) {
        if (principal != null) {
            String username = principal.getName(); 
            model.addAttribute("username", username);
        }
        return "dashboard";
    }
}
