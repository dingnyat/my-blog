package dou.ding.nyat.blog.controller.client;

import dou.ding.nyat.blog.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String login() {
        return "client/login";
    }
}
