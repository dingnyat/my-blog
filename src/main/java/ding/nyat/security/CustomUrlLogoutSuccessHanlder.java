package ding.nyat.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomUrlLogoutSuccessHanlder extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = "/login";
        this.getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        this.handle(request, response, authentication); // important
        super.onLogoutSuccess(request, response, authentication);
    }
}
