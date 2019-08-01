package dou.ding.nyat.blog.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "post")
@EntityListeners(AuditingEntityListener.class)
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
    private Integer positionInSeries;

    @Column(name = "is_actived", nullable = false)
    private boolean isActived;

    @Column(name = "is_comment_blocked", nullable = false)
    private boolean isCommentBlocked;

    @Column(name = "created_date", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdDate;

    @Column(name = "last_modified_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date lastModifiedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false,
            referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_post_author_author"))
    @CreatedBy
    private AuthorEntity author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_modified_by",
            referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_post_modified_by"))
    @LastModifiedBy
    private AuthorEntity lastModifiedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "series_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_post_series_series"))
    private SeriesEntity series;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "post_tag",
            joinColumns = {@JoinColumn(name = "post_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_post_post_tag"))},
            inverseJoinColumns = {@JoinColumn(name = "tag_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_post_tag_tag"))},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"post_id", "tag_id"})})
    private Set<TagEntity> tags;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> comments;

    public PostEntity() {
        this.isActived = false;
        this.isCommentBlocked = false;
    }
}
