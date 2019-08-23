package ding.nyat.entity;

import ding.nyat.security.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
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

    // TODO sửa tên biến
    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "account_role",
            joinColumns = {@JoinColumn(name = "account_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_account_account_role"))},
            inverseJoinColumns = {@JoinColumn(name = "role_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_account_role_role"))},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"account_id", "role_id"})})
    private Set<Role> roles;

    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private AuthorEntity author;

    public AccountEntity() {
        this.isActive = false;
    }
}
