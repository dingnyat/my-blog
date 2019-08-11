package ding.nyat.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class Category extends CommonModel<Integer> {
    private String code;
    private String name;
    private String description;
    private Set<Series> series;
    private Set<Tag> tags;
}
