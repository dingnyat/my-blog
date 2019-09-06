package ding.nyat.model;

import ding.nyat.annotation.Identifier;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class Post {
    @Identifier
    private Integer id;
    private String code;
    private String title;
    private String content;
    private Integer positionInSeries;
    private boolean isActived;
    private boolean isCommentBlocked;
    private String createdDate;
    private String lastModifiedDate;
    private String authorCode;
    private String authorName;
    private String seriesCode;
    private String seriesName;
    private Set<Tag> tags;
    private List<Comment> comments;

    public Post() {
        this.isActived = false;
        this.isCommentBlocked = false;
    }
}
