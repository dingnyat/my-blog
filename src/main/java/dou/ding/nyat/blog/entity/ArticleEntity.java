package dou.ding.nyat.blog.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "article")
public class ArticleEntity {

    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "code", nullable = false, unique = true, length = 64)
    private String code;

    @Column(name = "title", nullable = false, length = 2048)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "text")
    private String content;

    @Column(name = "time", nullable = false)
    @Temporal(value = TemporalType.DATE)
    private Date time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "series_id", nullable = false, insertable = false, updatable = false,
            referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_article_series_series"))
    private SeriesEntity articleSeries;

    @Column(name = "position_in_series", nullable = false)
    private int positionInSeries;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "article_author",
            joinColumns = {@JoinColumn(name = "article_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_article_article_author"))},
            inverseJoinColumns = {@JoinColumn(name = "author_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_article_author_author"))},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"article_id", "author_id"})})
    private Set<AuthorEntity> authors;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "article_tag",
            joinColumns = {@JoinColumn(name = "article_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_article_article_tag"))},
            inverseJoinColumns = {@JoinColumn(name = "tag_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_article_tag_tag"))},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"article_id", "tag_id"})})
    private Set<TagEntity> tags;

    @OneToMany(mappedBy = "article", fetch = FetchType.EAGER)
    private Set<CommentEntity> comments;

    public ArticleEntity() {
        this.isActive = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Set<AuthorEntity> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<AuthorEntity> authors) {
        this.authors = authors;
    }

    public Set<TagEntity> getTags() {
        return tags;
    }

    public void setTags(Set<TagEntity> tags) {
        this.tags = tags;
    }

    public SeriesEntity getArticleSeries() {
        return articleSeries;
    }

    public void setArticleSeries(SeriesEntity articleSeries) {
        this.articleSeries = articleSeries;
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

    public Set<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(Set<CommentEntity> comments) {
        this.comments = comments;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
