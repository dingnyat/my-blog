package dou.ding.nyat.blog.controller.client;

import dou.ding.nyat.blog.model.Article;
import dou.ding.nyat.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/user/{username}/workspace/new-article")
    public ModelAndView newArticle(@PathVariable("username") String username) {
        return new ModelAndView("client/new-article");
    }

    @PostMapping("/api/user/{username}/workspace/new-article")
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
