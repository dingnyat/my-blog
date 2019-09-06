package ding.nyat.model;

import ding.nyat.annotation.Identifier;
import lombok.Data;


@Data
public class Tag {
    @Identifier
    private Integer id;
    private String code;
    private String name;
}
