package ding.nyat.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Series extends CommonModel<Integer> {
    private String code;
    private String name;
    private String description;
}
