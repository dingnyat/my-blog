package ding.nyat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ding.nyat.annotation.Identifier;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Data
public class Author {
    @Identifier
    private Integer id;
    private String code;
    private String name;
    private String description;
    private String avatarUrl;
    private Set<SocialLink> socialLinks;
    @JsonIgnore
    private MultipartFile avatarFile;
}
