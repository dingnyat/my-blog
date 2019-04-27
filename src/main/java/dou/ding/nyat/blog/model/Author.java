package dou.ding.nyat.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public class Author extends CommonModel<Integer> {
    private String name;
    private String description;
    private String avatarUrl;
    private Set<SocialLink> socialLinks;
    @JsonIgnore
    private MultipartFile avatarFile;

    public Author() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<SocialLink> getSocialLinks() {
        return socialLinks;
    }

    public void setSocialLinks(Set<SocialLink> socialLinks) {
        this.socialLinks = socialLinks;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public MultipartFile getAvatarFile() {
        return avatarFile;
    }

    public void setAvatarFile(MultipartFile avatarFile) {
        this.avatarFile = avatarFile;
    }
}
