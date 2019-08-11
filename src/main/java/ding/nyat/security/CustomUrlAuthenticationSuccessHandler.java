package ding.nyat.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class CustomUrlAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        String username = authentication.getName();
        String targetUrl = "/";
        if (roles.contains("ROLE_AUTHOR")) {
            targetUrl = "/user/workspace";
        }
        if (response.isCommitted()) {
            System.out.println("Can't redirect");
            return;
        }
        this.getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
