package grauebcf.schoolapppro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String showDashboardPage() {
        return "dashboard"; // Assuming your dashboard Thymeleaf template is named "dashboard.html"
    }
}
