package ding.nyat.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class Post extends CommonModel<Integer> {

    private String code;
    private String title;
    private String summary;
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
