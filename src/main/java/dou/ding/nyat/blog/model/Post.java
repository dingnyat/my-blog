package dou.ding.nyat.blog.model;

import java.util.Date;
import java.util.Set;

public class Post extends CommonModel<Integer> {

    private String code;
    private String title;
    private String content;
    private int positionInSeries;
    private boolean isActived;
    private boolean isCommentBlocked;
    private Date createdDate;
    private Date lastUpdatedDate;
    private Author author;
    private Series series;
    private Set<Tag> tags;
    private Set<Comment> comments;

    public Post() {
        this.isActived = false;
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

    public int getPositionInSeries() {
        return positionInSeries;
    }

    public void setPositionInSeries(int positionInSeries) {
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
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
