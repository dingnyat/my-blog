package dou.ding.nyat.blog.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "account_verification_token")
public class AccountVerificationTokenEntity {

    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_account_account_token"))
    private AccountEntity account;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time", nullable = false)
    private Date time;

    public AccountVerificationTokenEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
