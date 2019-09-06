package ding.nyat.model;

import ding.nyat.util.search.SearchRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchPost extends SearchRequest {
    private Integer id;
    private String postCode;
    private String postTitle;
    private String authorId;
    private String authorCode;
    private Set<String> tags;
}
