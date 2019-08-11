package ding.nyat.controller.workspace;

import ding.nyat.model.Post;
import ding.nyat.model.Tag;
import ding.nyat.service.PostService;
import ding.nyat.service.SeriesService;
import ding.nyat.service.TagService;
import ding.nyat.util.datatable.DataTableRequest;
import ding.nyat.util.datatable.DataTableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PostController {

    @Autowired
    private SeriesService seriesService;

    @Autowired
    private TagService tagService;

    @Autowired
    private PostService postService;

    @GetMapping("/user/post")
    public String post() {
        return "workspace/post";
    }

    @PostMapping("/user/post/list")
    @ResponseBody
    private DataTableResponse<Post> posts(@RequestBody DataTableRequest dataTableRequest, Authentication authentication) {
        String curUsername = authentication.getName();
        List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        if (roles.contains("ROLE_ADMIN")) {
            List<Post> posts = postService.getTableData(dataTableRequest, "id", "title");
            DataTableResponse<Post> dataTableResponse = new DataTableResponse<>(posts);
            dataTableResponse.setDraw(dataTableRequest.getDraw());
            dataTableResponse.setRecordsFiltered(postService.countTableDataRecords(dataTableRequest, "id", "code"));
            dataTableResponse.setRecordsTotal(postService.countAllRecords());
            return dataTableResponse;
        }
        List<Post> posts = postService.getTableData(curUsername, dataTableRequest, "id", "title");
        DataTableResponse<Post> dataTableResponse = new DataTableResponse<>(posts);
        dataTableResponse.setDraw(dataTableRequest.getDraw());
        dataTableResponse.setRecordsFiltered(postService.countTableDataRecords(curUsername, dataTableRequest, "id", "code"));
        dataTableResponse.setRecordsTotal(postService.countAllRecords(curUsername));
        return dataTableResponse;
    }

    @GetMapping("/user/post/new")
    public String newPost(Model model) {
        model.addAttribute("seriesList", seriesService.getAllRecords());
        return "workspace/new-post";
    }

    @PostMapping("/user/post/get-tags")
    @ResponseBody
    public List<Tag> getTags() {
        return tagService.getAllRecords();
    }

    @PostMapping("/user/post/new")
    @ResponseBody
    public ResponseEntity<String> addNewPost(@RequestBody Post post) {
        try {
            postService.create(post);
            return new ResponseEntity<>("OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/post/update/{id}")
    private String update(@PathVariable("id") Integer id, Model model, Authentication authentication) {
        String curUsername = authentication.getName();
        List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        if (roles.contains("ROLE_ADMIN") || (roles.contains("ROLE_AUTHOR") && postService.isAuthor(curUsername, id))) {
            model.addAttribute("seriesList", seriesService.getAllRecords());
            model.addAttribute("postId", id);
            return "workspace/edit-post";
        } else {
            return "redirect:/access-denied";
        }
    }

    @PutMapping("/user/post/update")
    private ResponseEntity<String> update(@RequestBody Post post) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String curUsername = authentication.getName();
            List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            if (roles.contains("ROLE_ADMIN") || (roles.contains("ROLE_AUTHOR") && postService.isAuthor(curUsername, post.getId()))) {
                postService.update(post);
                return new ResponseEntity<>("OK", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("FORBIDEN", HttpStatus.FORBIDDEN);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/user/post/get/{id}")
    @ResponseBody
    private ResponseEntity<?> getPost(@PathVariable("id") Integer id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String curUsername = authentication.getName();
            List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            if (roles.contains("ROLE_ADMIN") || (roles.contains("ROLE_AUTHOR") && postService.isAuthor(curUsername, id))) {
                return new ResponseEntity<>(postService.getById(id), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("FORBIDEN", HttpStatus.FORBIDDEN);
            }
        } catch (Exception ignore) {
        }
        return new ResponseEntity<>("ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/user/post/delete/{id}")
    @ResponseBody
    private ResponseEntity<String> delete(@PathVariable("id") Integer postId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String curUsername = authentication.getName();
            List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            if (roles.contains("ROLE_ADMIN") || (roles.contains("ROLE_AUTHOR") && postService.isAuthor(curUsername, postId))) {
                postService.delete(postId);
                return new ResponseEntity<>("OK", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("FORBIDEN", HttpStatus.FORBIDDEN);
            }
        } catch (Exception ignore) {
        }
        return new ResponseEntity<>("ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/user/post/multiple-delete/{ids}")
    @ResponseBody
    private ResponseEntity<String> multipleDelete(@PathVariable("ids") List<Integer> postIds) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String curUsername = authentication.getName();
            List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            if (roles.contains("ROLE_ADMIN")) {
                postIds.forEach(id -> postService.delete(id));
                return new ResponseEntity<>("OK", HttpStatus.OK);
            } else if (roles.contains("ROLE_AUTHOR")) {
                for (Integer id : postIds) {
                    if (!postService.isAuthor(curUsername, id)) {
                        return new ResponseEntity<>("FORBIDEN", HttpStatus.FORBIDDEN);
                    }
                }
                postIds.forEach(id -> postService.delete(id));
                return new ResponseEntity<>("OK", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("FORBIDEN", HttpStatus.FORBIDDEN);
            }
        } catch (Exception ignore) {
        }
        return new ResponseEntity<>("ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
