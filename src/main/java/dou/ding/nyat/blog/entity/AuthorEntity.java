package dou.ding.nyat.blog.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "author")
public class AuthorEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @Column(name = "description", nullable = false, length = 1024)
    private String description;

    @Column(name = "avatar_url", length = 1024)
    private String avatarUrl;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_author_account_account"))
    private AccountEntity account;

    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    private Set<ArticleEntity> articles;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<SocialLinkEntity> socialLinks;

    public AuthorEntity() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<SocialLinkEntity> getSocialLinks() {
        return socialLinks;
    }

    public void setSocialLinks(Set<SocialLinkEntity> socialLinks) {
        this.socialLinks = socialLinks;
    }

    public Set<ArticleEntity> getArticles() {
        return articles;
    }

    public void setArticles(Set<ArticleEntity> articles) {
        this.articles = articles;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
