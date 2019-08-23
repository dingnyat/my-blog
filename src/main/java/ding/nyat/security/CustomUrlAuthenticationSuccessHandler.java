package ding.nyat.security;

import ding.nyat.config.CKFinderConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class CustomUrlAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        String targetUrl = "/";
        if (roles.contains(Role.ADMIN.getFullName())) {
            targetUrl = "/workspace";
            request.getSession().setAttribute(CKFinderConfig.CKFINDER_USER_ROLE_SESSION_VAR, Role.ADMIN.getName());
        } else if (roles.contains(Role.AUTHOR.getFullName())) {
            targetUrl = "/workspace";
            request.getSession().setAttribute(CKFinderConfig.CKFINDER_USER_ROLE_SESSION_VAR, Role.AUTHOR.getName());
        }
        if (response.isCommitted()) {
            System.out.println("Can't redirect");
            return;
        }
        this.getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
