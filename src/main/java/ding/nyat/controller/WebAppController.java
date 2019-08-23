package ding.nyat.controller;

import ding.nyat.security.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class WebAppController {
    @GetMapping("/file-manager")
    public String ckfinder(Authentication authentication, HttpServletResponse response) throws IOException {
        if (authentication != null && authentication.getPrincipal() != null) {
            List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            if (roles.contains(Role.AUTHOR.getFullName()) || roles.contains(Role.ADMIN.getFullName())) {
                return "ckfinder";
            }
        }
        response.sendRedirect("/access-denied");
        return null;
    }
}
