package ding.nyat.model;

import ding.nyat.annotation.Identifier;
import ding.nyat.util.Pair;
import lombok.Data;

import java.util.Set;

@Data
public class Series {
    @Identifier
    private Integer id;
    private String code;
    private String name;
    private String description;
    private Set<Pair<String, String>> posts;
}
