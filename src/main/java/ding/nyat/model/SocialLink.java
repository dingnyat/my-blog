package ding.nyat.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SocialLink extends CommonModel<Integer> {
    private String name;
    private String link;
}
