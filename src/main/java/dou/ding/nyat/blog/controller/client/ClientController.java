package dou.ding.nyat.blog.controller.client;

import dou.ding.nyat.blog.model.Article;
import dou.ding.nyat.blog.service.AccountService;
import dou.ding.nyat.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
public class ClientController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private ArticleService articleService;

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

    @GetMapping("/user/{username}/workspace/new-article")
    public ModelAndView newArticle(@PathVariable("username") String username) {
        return new ModelAndView("client/new-article");
    }

    @PostMapping("/user/{username}/workspace/new-article")
    @ResponseBody
    public ResponseEntity<?> newArticle(@PathVariable("username") String username, @RequestBody Article article) {
        try {
            article.setTime(new Date());
            article.setPositionInSeries(1);
            articleService.create(article);
            return new ResponseEntity<>("Successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Server error!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
