package ding.nyat.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SocialLink extends CommonModel<Integer> {
    private String name;
    private String link;
}
