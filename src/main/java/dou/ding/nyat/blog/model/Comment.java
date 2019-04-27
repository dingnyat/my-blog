package dou.ding.nyat.blog.model;

import java.util.Date;

public class Comment extends CommonModel<Integer> {
    private String writer;
    private String content;
    private Date time;
    private boolean isAccepted;
    private boolean isRemoved;

    public Comment() {
        this.isAccepted = false;
        this.isRemoved = false;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
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
