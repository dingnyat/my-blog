package ding.nyat.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Tag extends CommonModel<Integer> {
    private String code;
    private String name;
}
