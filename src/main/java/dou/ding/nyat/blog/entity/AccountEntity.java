package dou.ding.nyat.blog.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "account")
public class AccountEntity {

    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", nullable = false, unique = true, length = 128)
    private String username;

    @Column(name = "password", nullable = false, length = 512)
    private String password;

    @Column(name = "email", unique = true, length = 128)
    private String email;

    @Column(name = "is_actived", nullable = false)
    private boolean isActived;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "account_role",
            joinColumns = {@JoinColumn(name = "account_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_account_account_role"))},
            inverseJoinColumns = {@JoinColumn(name = "role_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_account_role_role"))},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"account_id", "role_id"})})
    private Set<RoleEntity> roles;

    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private AuthorEntity author;

    public AccountEntity() {
        this.isActived = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActived() {
        return isActived;
    }

    public void setActived(boolean actived) {
        isActived = actived;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    public AuthorEntity getAuthor() {
        return author;
    }

    public void setAuthor(AuthorEntity author) {
        this.author = author;
    }
}
