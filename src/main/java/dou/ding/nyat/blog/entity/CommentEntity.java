package dou.ding.nyat.blog.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "comment")
public class CommentEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "comment_by", nullable = false, length = 64)
    private String commentBy;

    @Column(name = "content", nullable = false, columnDefinition = "text")
    private String content;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_comment_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_comment_comment"))
    private Set<CommentEntity> childComments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_comment_post"))
    private PostEntity post;

    @Column(name = "is_accepted", nullable = false)
    private boolean isAccepted;

    @Column(name = "is_removed", nullable = false)
    private boolean isRemoved;

    public CommentEntity() {
        this.isAccepted = false;
        this.isRemoved = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Set<CommentEntity> getChildComments() {
        return childComments;
    }

    public void setChildComments(Set<CommentEntity> childComments) {
        this.childComments = childComments;
    }

    public PostEntity getPost() {
        return post;
    }

    public void setPost(PostEntity post) {
        this.post = post;
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
