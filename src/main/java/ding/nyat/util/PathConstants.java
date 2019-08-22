package ding.nyat.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathConstants {
    // folders
    public static final String UPLOAD_BASE_DIR = System.getProperty("user.dir") + "/upload";
    public static final String IMAGE_UPLOAD_DIR = UPLOAD_BASE_DIR + "/image";
    public static final String AVATAR_UPLOAD_DIR = UPLOAD_BASE_DIR + "/avatar";

    // urls
    public static final String DOWNLOAD_PREFIX_URL = "/public";
    public static final String IMAGE_PREFIX_URL = "/image";

    static {
        for (Field field : PathConstants.class.getFields()) {
            if (field.getName().toUpperCase().endsWith("_DIR")) {
                try {
                    Path path = Paths.get((String) field.get(PathConstants.class.newInstance()));
                    if (!Files.exists(path)) {
                        Files.createDirectories(path);
                    }
                } catch (IllegalAccessException | InstantiationException | IOException e) {
                    System.out.println(e + "-> Can't not create directory <" + field.getName() + ">");
                }
            }
        }
    }
}
