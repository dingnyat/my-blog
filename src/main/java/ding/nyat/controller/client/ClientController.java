package ding.nyat.controller.client;

import ding.nyat.model.Author;
import ding.nyat.model.Comment;
import ding.nyat.model.Post;
import ding.nyat.service.AccountService;
import ding.nyat.service.AuthorService;
import ding.nyat.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClientController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private PostService postService;

    @Autowired
    private AuthorService authorService;

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

    @GetMapping("/post/{code}")
    public String post(@PathVariable("code") String code, Model model) {
        Post post = postService.getByCode(code);
        if (post == null) {
            model.addAttribute("errorCodeMessage", "Error 404, Not Found!");
            model.addAttribute("message", "Sorry, Something went wrong!");
            return "error/error";
        }
        model.addAttribute("post", post);
        return "client/post";
    }

    @GetMapping("/author/{author-code}")
    public String authorProfile(@PathVariable("author-code") String authorCode, Model model) {
        Author author = authorService.getByCode(authorCode);
        if (author == null) {
            model.addAttribute("errorCodeMessage", "Error 404, Not Found!");
            model.addAttribute("message", "Sorry, Something went wrong!");
            return "error/error";
        }
        model.addAttribute("author", author);
        return "client/author-profile";
    }

    @PostMapping("/post/add-comment")
    public ResponseEntity<String> addComment(@ModelAttribute Comment comment) {
        try {
            if (comment.getPostId() == null && comment.getParentCommentId() != null) {
                // why not add just only comment? comment has parentcommentid itself
                postService.addChildComment(comment.getParentCommentId(), comment);
            }
            if (comment.getPostId() != null && comment.getParentCommentId() == null) {
                // why not add just only comment? comment has parentcommentid itself
                postService.addComment(comment.getPostId(), comment);
            }
            return new ResponseEntity<>("OK", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
