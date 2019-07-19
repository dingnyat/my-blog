package dou.ding.nyat.blog.controller;

import dou.ding.nyat.blog.util.FileStore;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Controller
public class DownloadController {

    @GetMapping(value = "/public/image/{imgName}")
    public void loadImage(@PathVariable("imgName") String imgName, HttpServletResponse response) throws IOException {
        String filePath = FileStore.IMAGE_UPLOAD_PATH + File.separator + imgName;
        File file = new File(filePath);
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        if (file.isFile()) {
            Files.copy(file.toPath(), response.getOutputStream());
        }
    }
}
