package dou.ding.nyat.blog.model;

import java.util.Date;
import java.util.Set;

public class Comment extends CommonModel<Integer> {

    private String commentBy;
    private String content;
    private Date createdDate;
    private Set<Comment> childComments;
    private boolean isAccepted;
    private boolean isRemoved;

    public Comment() {
        this.isAccepted = false;
        this.isRemoved = false;
    }

    public String getCommentBy() {
        return commentBy;
    }

    public void setCommentBy(String commentBy) {
        this.commentBy = commentBy;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Set<Comment> getChildComments() {
        return childComments;
    }

    public void setChildComments(Set<Comment> childComments) {
        this.childComments = childComments;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public boolean isRemoved() {
        return isRemoved;
    }

    public void setRemoved(boolean removed) {
        isRemoved = removed;
    }
}
