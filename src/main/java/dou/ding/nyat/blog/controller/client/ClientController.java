package dou.ding.nyat.blog.controller.client;

import dou.ding.nyat.blog.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

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
        System.out.println((new BCryptPasswordEncoder(12)).encode("zxcvbnm"));
        return "client/login";
    }

    @GetMapping("/user/{username}/workspace")
    public ModelAndView workspace(@PathVariable("username") String username) {
        return new ModelAndView("client/workspace", "author", accountService.getAuthorByUsername(username));
    }

}
