package ding.nyat.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Series extends CommonModel<Integer> {
    private String code;
    private String name;
    private String description;
}
