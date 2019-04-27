package dou.ding.nyat.blog.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comment")
public class CommentEntity {
    @Id
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "writer", nullable = false, length = 64)
    private String writer;

    @Column(name = "content", nullable = false, columnDefinition = "text")
    private String content;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "time", nullable = false)
    private Date time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_comment_article"))
    private ArticleEntity article;

    @Column(name = "is_accepted", nullable = false)
    private boolean isAccepted;

    @Column(name = "is_removed", nullable = false)
    private boolean isRemoved;

    public CommentEntity() {
        this.isAccepted = false;
        this.isRemoved = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public ArticleEntity getArticle() {
        return article;
    }

    public void setArticle(ArticleEntity article) {
        this.article = article;
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
