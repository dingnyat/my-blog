package dou.ding.nyat.blog.entity;

import javax.persistence.*;

@Entity
@Table(name = "social_link")
public class SocialLinkEntity {
    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @Column(name = "link", nullable = false, length = 1024)
    private String link;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false, insertable = false, updatable = false,
            referencedColumnName = "id", foreignKey = @ForeignKey(name = "author_social_social"))
    private AuthorEntity author;

    public SocialLinkEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public AuthorEntity getAuthor() {
        return author;
    }

    public void setAuthor(AuthorEntity author) {
        this.author = author;
    }
}
