package ding.nyat.controller;

import ding.nyat.model.Author;
import ding.nyat.model.Comment;
import ding.nyat.model.Post;
import ding.nyat.security.AdvancedSecurityContextHolder;
import ding.nyat.security.Role;
import ding.nyat.service.AccountService;
import ding.nyat.service.AuthorService;
import ding.nyat.service.PostService;
import ding.nyat.util.search.SearchRequest;
import ding.nyat.util.search.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class WebAppController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private PostService postService;

    @Autowired
    private AuthorService authorService;

    @GetMapping("/")
    public String homepage() {
        return "index";
    }

    @GetMapping("/login")
    public String login(Authentication authentication) {
        if (AdvancedSecurityContextHolder.isLoggedIn()) {
            return "redirect:/workspace";
        }
        return "login";
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
        return "author-profile";
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
        return "post";
    }

    @PostMapping("/post/add-comment")
    public ResponseEntity<String> addComment(@ModelAttribute Comment comment) {
        try {
            if (comment.getPostId() == -1 && comment.getParentCommentId() != -1) {
                // why not add just only comment? comment has parentcommentid itself
                postService.addChildComment(comment.getParentCommentId(), comment);
            }
            if (comment.getPostId() != -1 && comment.getParentCommentId() == -1) {
                // why not add just only comment? comment has parentcommentid itself
                postService.addComment(comment.getPostId(), comment);
            }
            return new ResponseEntity<>("OK", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/post/search")
    public ResponseEntity<SearchResponse<Post>> searchPost(@RequestBody SearchRequest searchRequest) {
        try {
            return new ResponseEntity<>(postService.search(searchRequest), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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
