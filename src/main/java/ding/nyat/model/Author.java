package ding.nyat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class Author extends CommonModel<Integer> {
    private String code;
    private String name;
    private String description;
    private String avatarUrl;
    private Set<SocialLink> socialLinks;
    @JsonIgnore
    private MultipartFile avatarFile;
}
