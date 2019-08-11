package ding.nyat.controller;

import ding.nyat.util.PathConstants;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Controller
public class DownloadController {

    @RequestMapping(value = "/file/{fileName:.+}")
    public void downloadFile(@PathVariable(value = "fileName") String fileName, HttpServletResponse response) {
        try {
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", String.format("inline; filename=\"%s\"", fileName));
            File file = new File(fileName);
            Files.copy(file.toPath(), response.getOutputStream());
        } catch (IOException ex) {
            try {
                response.sendRedirect("/404");
            } catch (IOException ignored) { // wtf?
            }
        }
    }

    @RequestMapping(value = PathConstants.DOWNLOAD_PREFIX_URL + "/image/{image:.+}",
            method = RequestMethod.GET,
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
    @ResponseBody
    public byte[] loadImage(@PathVariable(value = "image") String image) {
        try {
            String filePath = PathConstants.IMAGE_UPLOAD_DIR + File.separator + image;
            File file = new File(filePath);
            return Files.readAllBytes(file.toPath());
        } catch (Exception e) {
            return new byte[0];
        }
    }
}
