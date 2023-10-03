package grauebcf.schoolapppro.authentication;


import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {


    public static String REDIRECT_URL = "REDIRECT_URL";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        Object redirectURLObject = request.getSession().getAttribute("REDIRECT_URL");

        if (redirectURLObject != null) {
            setDefaultTargetUrl(redirectURLObject.toString());
        } else {
            // Change the default target URL to the one you want
            setDefaultTargetUrl("/dashboard"); // For example, change to "/dashboard"
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}

