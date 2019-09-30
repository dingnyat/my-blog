package ding.nyat.controller;

import com.ckfinder.connector.utils.PathUtils;
import ding.nyat.util.PathConstants;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = PathConstants.IMAGE_PREFIX_URL + "/{type}/{image:.+}",
            method = RequestMethod.GET,
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
    @ResponseBody
    public byte[] loadImage(@PathVariable("type") String type, @PathVariable(value = "image") String image) {
        try {
            String filePath = "";
            if (type.equals("public")) {
                filePath = PathUtils.addSlashToEnd(PathConstants.IMAGE_UPLOAD_DIR) + image;
            } else if (type.equals("user")) {
                filePath = PathUtils.addSlashToEnd(PathConstants.AVATAR_UPLOAD_DIR) + image;
            }
            File file = new File(filePath);
            return Files.readAllBytes(file.toPath());
        } catch (Exception e) {
            return new byte[0];
        }
    }

    @GetMapping("/favicon.ico")
    @ResponseBody // disabling favicon leads to 404 error, use this to avoid it
    void returnNoFavicon() {
    }
}
