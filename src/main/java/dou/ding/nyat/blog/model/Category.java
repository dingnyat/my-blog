package dou.ding.nyat.blog.model;

import lombok.Data;

import java.util.Set;

@Data
public class Category extends CommonModel<Integer> {
    private String code;
    private String name;
    private String description;
    private Set<Series> series;
    private Set<Tag> tags;
}
