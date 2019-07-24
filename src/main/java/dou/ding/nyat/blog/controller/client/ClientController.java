package dou.ding.nyat.blog.controller.client;

import dou.ding.nyat.blog.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/")
    public String homepage() {
        return "client/index";
    }

    @GetMapping("/login")
    public String login(Authentication authentication) {
        if (authentication != null && authentication.getName() != null) {
            return "redirect:/user/workspace";
        }
        return "client/login";
    }
}
