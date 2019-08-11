package ding.nyat.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class Comment extends CommonModel<Integer> {
    private String commentBy;
    private String content;
    private String createdDate;
    private List<Comment> childComments;
    private boolean isAccepted;
    private boolean isRemoved;

    private Integer postId;
    private Integer parentCommentId;

    public Comment() {
        this.isAccepted = false;
        this.isRemoved = false;
    }
}
