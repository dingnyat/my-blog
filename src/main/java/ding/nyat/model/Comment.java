package ding.nyat.model;

import ding.nyat.annotation.Identifier;
import lombok.Data;

import java.util.List;

@Data
public class Comment {
    @Identifier
    private Integer id;
    private String commentBy;
    private String content;
    private String createdDate;
    private List<Comment> childComments;
    private boolean isAccepted;
    private boolean isRemoved;

    private int postId;
    private int parentCommentId;

    public Comment() {
        this.isAccepted = false;
        this.isRemoved = false;
    }
}
