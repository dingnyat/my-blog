package dou.ding.nyat.blog.controller.admin;

import dou.ding.nyat.blog.model.Category;
import dou.ding.nyat.blog.model.Tag;
import dou.ding.nyat.blog.service.CategoryService;
import dou.ding.nyat.blog.service.SeriesService;
import dou.ding.nyat.blog.service.TagService;
import dou.ding.nyat.blog.util.datatable.DataTableRequest;
import dou.ding.nyat.blog.util.datatable.DataTableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private SeriesService seriesService;

    @GetMapping("/category")
    public String category(Model model) {
        model.addAttribute("seriesList", seriesService.getAllRecords());
        return "admin/category";
    }

    @PostMapping("/category/list")
    @ResponseBody
    public DataTableResponse<Category> list(@RequestBody DataTableRequest dataTableRequest) {
        List<Category> categories = categoryService.getTableData(dataTableRequest, "id", "code", "name");
        DataTableResponse<Category> dataTableResponse = new DataTableResponse<>(categories);
        dataTableResponse.setDraw(dataTableRequest.getDraw());
        dataTableResponse.setRecordsFiltered(categoryService.countTableDataRecords(dataTableRequest, "id", "code", "name"));
        dataTableResponse.setRecordsTotal(categoryService.countAllRecords());
        return dataTableResponse;
    }

    @PostMapping("/category/add")
    @ResponseBody
    public ResponseEntity<String> add(@ModelAttribute Category category) {
        try {
            categoryService.create(category);
            return new ResponseEntity<>("OK", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/category/add-tag/{id}")
    @ResponseBody
    public ResponseEntity<String> addTag(@PathVariable("id") Integer categoryId, @ModelAttribute Tag tag) {
        try {
            categoryService.addTag(categoryId, tag);
            return new ResponseEntity<>("OK", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/category/delete-tag/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteTag(@PathVariable("id") Integer tagId) {
        try {
            tagService.delete(tagId);
            return new ResponseEntity<>("OK", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/category/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
        try {
            categoryService.delete(id);
            return new ResponseEntity<>("OK", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/category/update")
    @ResponseBody
    public ResponseEntity<String> update(@ModelAttribute Category category) {
        try {
            categoryService.update(category);
            return new ResponseEntity<>("OK", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/category/multiple-delete/{ids}")
    @ResponseBody
    public ResponseEntity<String> multipleDelete(@PathVariable("ids") List<Integer> ids) {
        try {
            for (Integer id : ids) {
                categoryService.delete(id);
            }
            return new ResponseEntity<>("OK", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/category/deselect-series")
    @ResponseBody
    public ResponseEntity<String> deselectSeries(@RequestParam("seriesId") Integer seriesId, @RequestParam("categoryId") Integer categoryId) {
        try {
            categoryService.deselectSeries(seriesId, categoryId);
            return new ResponseEntity<>("OK", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/category/link-series")
    @ResponseBody
    public ResponseEntity<String> linkSeries(@RequestParam("seriesId") Integer seriesId, @RequestParam("categoryId") Integer categoryId) {
        try {
            categoryService.linkSeries(seriesId, categoryId);
            return new ResponseEntity<>("OK", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
