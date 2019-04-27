package dou.ding.nyat.blog.model;

import java.util.Date;
import java.util.Set;

public class Article extends CommonModel<Integer> {
    private String code;
    private String title;
    private String content;
    private Date time;
    private int positionInSeries;
    private boolean isActive;
    private Set<Author> authors;
    private Set<Tag> tags;
    private Set<Comment> comments;

    public Article() {
        this.isActive = false;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
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

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
