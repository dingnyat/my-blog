package ding.nyat.model;

import ding.nyat.annotation.Identifier;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
public class Category {
    @Identifier
    private Integer id;
    private String code;
    private String name;
    private String description;
    private Set<Series> series;
    private Set<Tag> tags;
}
