package ding.nyat.model;

import ding.nyat.annotation.Identifier;
import lombok.Data;

@Data
public class SocialLink {
    @Identifier
    private Integer id;
    private String name;
    private String link;
}
