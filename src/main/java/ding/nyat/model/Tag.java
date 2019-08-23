package ding.nyat.model;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class Tag extends CommonModel<Integer> {
    private String code;
    private String name;
}
