package dou.ding.nyat.blog.model;

import java.util.Set;

public class Post extends CommonModel<Integer> {

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
    private Set<Comment> comments;

    public Post() {
        this.isActived = false;
        this.isCommentBlocked = false;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getPositionInSeries() {
        return positionInSeries;
    }

    public void setPositionInSeries(Integer positionInSeries) {
        this.positionInSeries = positionInSeries;
    }

    public boolean isActived() {
        return isActived;
    }

    public void setActived(boolean actived) {
        isActived = actived;
    }

    public boolean isCommentBlocked() {
        return isCommentBlocked;
    }

    public void setCommentBlocked(boolean commentBlocked) {
        isCommentBlocked = commentBlocked;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getAuthorCode() {
        return authorCode;
    }

    public void setAuthorCode(String authorCode) {
        this.authorCode = authorCode;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getSeriesCode() {
        return seriesCode;
    }

    public void setSeriesCode(String seriesCode) {
        this.seriesCode = seriesCode;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
