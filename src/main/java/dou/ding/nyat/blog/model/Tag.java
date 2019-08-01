package dou.ding.nyat.blog.model;

import lombok.Data;

@Data
public class Tag extends CommonModel<Integer> {
    private String code;
    private String name;
}
