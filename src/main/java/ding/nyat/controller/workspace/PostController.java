package ding.nyat.controller.workspace;

import ding.nyat.model.Post;
import ding.nyat.model.Tag;
import ding.nyat.security.AdvancedSecurityContextHolder;
import ding.nyat.security.Role;
import ding.nyat.security.UserPrincipal;
import ding.nyat.service.PostService;
import ding.nyat.service.SeriesService;
import ding.nyat.service.TagService;
import ding.nyat.util.datatable.DataTableRequest;
import ding.nyat.util.datatable.DataTableResponse;
import ding.nyat.util.search.SearchCriterion;
import ding.nyat.util.search.SearchOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    private DataTableResponse<Post> posts(@RequestBody DataTableRequest dataTableRequest) {
        UserPrincipal userPrincipal = AdvancedSecurityContextHolder.getUserPrincipal();
        List<SearchCriterion> searchCriteria = new ArrayList<>();
        if (!userPrincipal.hasAnyRoles(Role.ADMIN.getFullName()) && userPrincipal.hasAnyRoles(Role.AUTHOR.getFullName())) {
            searchCriteria.add(new SearchCriterion("author.code", SearchOperator.EQUALITY, userPrincipal.getAuthorCode()));
        }
        dataTableRequest.setSearchCriteria(searchCriteria);

        DataTableResponse<Post> dataTableResponse = new DataTableResponse<>();
        dataTableResponse.setDraw(dataTableRequest.getDraw());
        dataTableResponse.setData(postService.getTableData(dataTableRequest));
        dataTableResponse.setRecordsFiltered(postService.countTableDataRecords(dataTableRequest));
        dataTableResponse.setRecordsTotal(postService.countAllRecords());
        return dataTableResponse;
    }

    @GetMapping("/user/post/new")
    public String newPost(Model model) {
        model.addAttribute("seriesList", seriesService.readAll());
        return "workspace/new-post";
    }

    @PostMapping("/user/post/get-tags")
    @ResponseBody
    public List<Tag> getTags() {
        return tagService.readAll();
    }

    @PostMapping("/user/post/new")
    @ResponseBody
    public ResponseEntity<String> addNewPost(@RequestBody Post post) {
        postService.create(post);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @GetMapping("/user/post/update/{id}")
    private String update(@PathVariable("id") Integer id, Model model, Authentication authentication) {
        UserPrincipal userPrincipal = AdvancedSecurityContextHolder.getUserPrincipal();
        if (userPrincipal.hasAnyRoles(Role.ADMIN.getFullName(), Role.AUTHOR.getFullName()) && postService.checkOwnership(userPrincipal.getUsername(), id)) {
            model.addAttribute("seriesList", seriesService.readAll());
            model.addAttribute("postId", id);
            return "workspace/edit-post";
        } else {
            return "redirect:/access-denied";
        }
    }

    @PutMapping("/user/post/update")
    private ResponseEntity<String> update(@RequestBody Post post) {
        try {
            UserPrincipal userPrincipal = AdvancedSecurityContextHolder.getUserPrincipal();
            if (userPrincipal.hasAnyRoles(Role.ADMIN.getFullName(), Role.AUTHOR.getFullName()) && postService.checkOwnership(userPrincipal.getUsername(), post.getId())) {
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
            UserPrincipal userPrincipal = AdvancedSecurityContextHolder.getUserPrincipal();
            if (userPrincipal.hasAnyRoles(Role.ADMIN.getFullName(), Role.AUTHOR.getFullName()) && postService.checkOwnership(userPrincipal.getUsername(), id)) {
                return new ResponseEntity<>(postService.read(id), HttpStatus.OK);
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
            UserPrincipal userPrincipal = AdvancedSecurityContextHolder.getUserPrincipal();
            if (userPrincipal.hasAnyRoles(Role.ADMIN.getFullName(), Role.AUTHOR.getFullName()) && postService.checkOwnership(userPrincipal.getUsername(), postId)) {
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
            UserPrincipal userPrincipal = AdvancedSecurityContextHolder.getUserPrincipal();
            if (userPrincipal.hasAnyRoles(Role.ADMIN.getFullName())) {
                postIds.forEach(id -> postService.delete(id));
                return new ResponseEntity<>("OK", HttpStatus.OK);
            } else if (userPrincipal.hasAnyRoles(Role.AUTHOR.getFullName())) {
                for (Integer id : postIds) {
                    if (!postService.checkOwnership(userPrincipal.getUsername(), id)) {
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
