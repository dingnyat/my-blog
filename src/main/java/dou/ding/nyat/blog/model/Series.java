package dou.ding.nyat.blog.model;

import lombok.Data;

@Data
public class Series extends CommonModel<Integer> {
    private String code;
    private String name;
    private String description;
}
