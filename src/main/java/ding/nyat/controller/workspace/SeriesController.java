package ding.nyat.controller.workspace;

import ding.nyat.model.Series;
import ding.nyat.service.SeriesService;
import ding.nyat.util.datatable.DataTableRequest;
import ding.nyat.util.datatable.DataTableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class SeriesController {

    @Autowired
    private SeriesService seriesService;

    @GetMapping("/series")
    public String series() {
        return "workspace/series";
    }

    @PostMapping("/series/list")
    @ResponseBody
    public DataTableResponse<Series> list(@RequestBody DataTableRequest dataTableRequest) {
        List<Series> series = seriesService.getTableData(dataTableRequest, "id", "code", "name");
        DataTableResponse<Series> dataTableResponse = new DataTableResponse<>(series);
        dataTableResponse.setDraw(dataTableRequest.getDraw());
        dataTableResponse.setRecordsFiltered(seriesService.countTableDataRecords(dataTableRequest, "id", "code", "name"));
        dataTableResponse.setRecordsTotal(seriesService.countAllRecords());
        return dataTableResponse;
    }

    @PostMapping("/series/add")
    @ResponseBody
    public ResponseEntity<String> add(@ModelAttribute Series series) {
        try {
            seriesService.create(series);
            return new ResponseEntity<>("OK", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/series/update")
    @ResponseBody
    public ResponseEntity<String> update(@ModelAttribute Series series) {
        try {
            seriesService.update(series);
            return new ResponseEntity<>("OK", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/series/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
        try {
            seriesService.delete(id);
            return new ResponseEntity<>("OK", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/series/multiple-delete/{ids}")
    @ResponseBody
    public ResponseEntity<String> multipleDelete(@PathVariable("ids") List<Integer> ids) {
        try {
            for (Integer id : ids) {
                seriesService.delete(id);
            }
            return new ResponseEntity<>("OK", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
