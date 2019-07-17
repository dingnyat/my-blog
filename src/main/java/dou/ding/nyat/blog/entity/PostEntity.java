package dou.ding.nyat.blog.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "post")
public class PostEntity {

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

    @Column(name = "position_in_series")
    private int positionInSeries;

    @Column(name = "is_actived", nullable = false)
    private boolean isActived;

    @Column(name = "is_comment_blocked", nullable = false)
    private boolean isCommentBlocked;

    @Column(name = "created_date", nullable = false)
    @Temporal(value = TemporalType.DATE)
    private Date createdDate;

    @Column(name = "last_updated_date", nullable = false)
    @Temporal(value = TemporalType.DATE)
    private Date lastUpdatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false,
            referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_post_author_author"))
    private AuthorEntity author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "series_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_post_series_series"))
    private SeriesEntity series;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "post_tag",
            joinColumns = {@JoinColumn(name = "post_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_post_post_tag"))},
            inverseJoinColumns = {@JoinColumn(name = "tag_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_post_tag_tag"))},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"post_id", "tag_id"})})
    private Set<TagEntity> tags;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER)
    private Set<CommentEntity> comments;

    public PostEntity() {
        this.isActived = false;
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

    public AuthorEntity getAuthor() {
        return author;
    }

    public void setAuthor(AuthorEntity author) {
        this.author = author;
    }

    public SeriesEntity getSeries() {
        return series;
    }

    public void setSeries(SeriesEntity series) {
        this.series = series;
    }

    public Set<TagEntity> getTags() {
        return tags;
    }

    public void setTags(Set<TagEntity> tags) {
        this.tags = tags;
    }

    public Set<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(Set<CommentEntity> comments) {
        this.comments = comments;
    }
}
