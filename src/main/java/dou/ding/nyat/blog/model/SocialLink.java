package dou.ding.nyat.blog.model;

import lombok.Data;

@Data
public class SocialLink extends CommonModel<Integer> {
    private String name;
    private String link;
}
