package dou.ding.nyat.blog.controller.client;

import dou.ding.nyat.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/api/get-all-tags")
    @ResponseBody
    public ResponseEntity<?> getAllTags() {
        try {
            return new ResponseEntity<>(tagService.getAllRecords(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Server error!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
