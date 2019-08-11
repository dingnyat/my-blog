package ding.nyat.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Role extends CommonModel<Integer> {
    private String name;
}
