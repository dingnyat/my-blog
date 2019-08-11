package ding.nyat.security;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "persistent_login")
public class PersistentLogin {
    @Id
    @Column(name = "series", nullable = false, length = 128)
    private String series;

    @Column(name = "username", nullable = false, length = 128)
    private String username;

    @Column(name = "token", nullable = false, length = 265)
    private String token;

    @Column(name = "last_used", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUsed;

    public PersistentLogin() {
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Date lastUsed) {
        this.lastUsed = lastUsed;
    }
}
