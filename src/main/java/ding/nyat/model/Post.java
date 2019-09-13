package ding.nyat.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import ding.nyat.annotation.Identifier;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Post {
    @Identifier
    private Integer id;
    private String code;
    private String title;
    private String description;
    private String content;
    private Integer positionInSeries;
    private Boolean active;
    private Boolean commentBlocked;
    private String createdDate;
    private String lastModifiedDate;
    private String authorCode;
    private String authorName;
    private String seriesCode;
    private String seriesName;
    private Set<Tag> tags;
    private List<Comment> comments;
}
