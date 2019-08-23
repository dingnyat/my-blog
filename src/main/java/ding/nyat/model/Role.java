package ding.nyat.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Role extends CommonModel<Integer> {
    private String name;
}
