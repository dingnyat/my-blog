package ding.nyat.controller;

import ding.nyat.model.*;
import ding.nyat.security.AdvancedSecurityContextHolder;
import ding.nyat.security.Role;
import ding.nyat.service.*;
import ding.nyat.util.search.SearchCriterion;
import ding.nyat.util.search.SearchOperator;
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
import java.util.Collections;
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

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private SeriesService seriesService;

    @GetMapping("/")
    public String homepage(Model model) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setLength(25);
        searchRequest.setDraw(0);
        searchRequest.setStart(searchRequest.getDraw() * searchRequest.getLength());
        model.addAttribute("postsResp", postService.search(searchRequest));
        return "index";
    }

    @GetMapping("/page/{pageNo}")
    public String homepage(@PathVariable("pageNo") Integer pageNo, Model model) {
        if (pageNo != null && pageNo <= 1) return "redirect:/";
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setLength(25);
        searchRequest.setDraw(pageNo == null ? 0 : pageNo - 1);
        searchRequest.setStart(searchRequest.getDraw() * searchRequest.getLength());
        model.addAttribute("postsResp", postService.search(searchRequest));
        return "index";
    }

    @GetMapping("/login")
    public String login(Authentication authentication) {
        if (AdvancedSecurityContextHolder.isLoggedIn()) {
            return "redirect:/workspace";
        }
        return "login";
    }

    @GetMapping("/author/{authorCode}")
    public String authorProfile(@PathVariable("authorCode") String authorCode, Model model,
                                @RequestParam(value = "page", required = false) Integer pageNo) {
        Author author = authorService.getByCode(authorCode);
        if (author == null) {
            model.addAttribute("errorCodeMessage", "Error 404, Not Found!");
            model.addAttribute("message", "Sorry, Something went wrong!");
            return "error/error";
        }
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setLength(15);
        searchRequest.setDraw(pageNo == null ? 0 : (pageNo < 1 ? 0 : pageNo - 1));
        searchRequest.setStart(searchRequest.getDraw() * searchRequest.getLength());
        searchRequest.setSearchCriteria(Collections.singletonList(
                new SearchCriterion("authorCode", SearchOperator.EQUALITY, authorCode)));
        model.addAttribute("postsResp", postService.search(searchRequest));
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
        if (post.getSeriesCode() != null) {
            model.addAttribute("postSeries", seriesService.getByCode(post.getSeriesCode()));
        }
        return "post";
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

    @PostMapping("/post/search")
    public ResponseEntity<SearchResponse<Post>> searchPost(@RequestBody SearchRequest searchRequest) {
        try {
            return new ResponseEntity<>(postService.search(searchRequest), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/category/{categoryCode}")
    public String category(@PathVariable("categoryCode") String categoryCode, Model model) {
        Category category = categoryService.getByCode(categoryCode);
        if (category == null) return "redirect:/404";
        model.addAttribute("category", category);

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setLength(25);
        searchRequest.setDraw(0);
        searchRequest.setStart(searchRequest.getDraw() * searchRequest.getLength());
        searchRequest.setSearchCriteria(Collections.singletonList(
                new SearchCriterion("categoryCode", SearchOperator.EQUALITY, categoryCode)));
        model.addAttribute("postsResp", postService.search(searchRequest));

        return "category";
    }

    @GetMapping("/category/{categoryCode}/page/{pageNo}")
    public String category(@PathVariable("categoryCode") String categoryCode,
                           @PathVariable("pageNo") Integer pageNo, Model model) {
        if (pageNo != null && pageNo <= 1) return "redirect:/category/" + categoryCode;

        Category category = categoryService.getByCode(categoryCode);
        if (category == null) return "redirect:/404";
        model.addAttribute("category", category);

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setLength(25);
        searchRequest.setDraw(pageNo == null ? 0 : pageNo - 1);
        searchRequest.setStart(searchRequest.getDraw() * searchRequest.getLength());
        searchRequest.setSearchCriteria(Collections.singletonList(
                new SearchCriterion("categoryCode", SearchOperator.EQUALITY, categoryCode)));
        model.addAttribute("postsResp", postService.search(searchRequest));

        return "category";
    }

    @GetMapping("/tag/{tagCode}")
    public String tag(@PathVariable("tagCode") String tagCode, Model model) {
        Tag tag = tagService.getByCode(tagCode);
        if (tag == null) return "redirect:/404";
        model.addAttribute("tag", tag);

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setLength(25);
        searchRequest.setDraw(0);
        searchRequest.setStart(searchRequest.getDraw() * searchRequest.getLength());
        searchRequest.setSearchCriteria(Collections.singletonList(
                new SearchCriterion("tagCode", SearchOperator.EQUALITY, tagCode)));
        model.addAttribute("postsResp", postService.search(searchRequest));

        return "tag";
    }

    @GetMapping("/tag/{tagCode}/page/{pageNo}")
    public String tag(@PathVariable("tagCode") String tagCode,
                      @PathVariable("pageNo") Integer pageNo, Model model) {
        if (pageNo != null && pageNo <= 1) return "redirect:/tag/" + tagCode;

        Tag tag = tagService.getByCode(tagCode);
        if (tag == null) return "redirect:/404";
        model.addAttribute("tag", tag);

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setLength(25);
        searchRequest.setDraw(pageNo == null ? 0 : pageNo - 1);
        searchRequest.setStart(searchRequest.getDraw() * searchRequest.getLength());
        searchRequest.setSearchCriteria(Collections.singletonList(
                new SearchCriterion("tagCode", SearchOperator.EQUALITY, tagCode)));
        model.addAttribute("postsResp", postService.search(searchRequest));

        return "tag";
    }

    @GetMapping("/series/{seriesCode}")
    public String series(@PathVariable("seriesCode") String seriesCode, Model model) {
        Series series = seriesService.getByCode(seriesCode);
        if (series == null) return "redirect:/404";
        model.addAttribute("series", series);

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setLength(25);
        searchRequest.setDraw(0);
        searchRequest.setStart(searchRequest.getDraw() * searchRequest.getLength());
        searchRequest.setSearchCriteria(Collections.singletonList(
                new SearchCriterion("seriesCode", SearchOperator.EQUALITY, seriesCode)));
        model.addAttribute("postsResp", postService.search(searchRequest));

        return "series";
    }

    @GetMapping("/series/{seriesCode}/page/{pageNo}")
    public String series(@PathVariable("seriesCode") String seriesCode,
                         @PathVariable("pageNo") Integer pageNo, Model model) {
        if (pageNo != null && pageNo <= 1) return "redirect:/series/" + seriesCode;

        Series series = seriesService.getByCode(seriesCode);
        if (series == null) return "redirect:/404";
        model.addAttribute("series", series);

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setLength(25);
        searchRequest.setDraw(pageNo == null ? 0 : pageNo - 1);
        searchRequest.setStart(searchRequest.getDraw() * searchRequest.getLength());
        searchRequest.setSearchCriteria(Collections.singletonList(
                new SearchCriterion("seriesCode", SearchOperator.EQUALITY, seriesCode)));
        model.addAttribute("postsResp", postService.search(searchRequest));

        return "series";
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

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        Post post = postService.read(5);
        post.setId(null);
        String code = post.getCode();
        for (int i = 1; i <= 50; i++) {
            post.setCode(code + i);
            post.setPositionInSeries(i + 100);
            postService.create(post);
        }
        return "ok";
    }
}
