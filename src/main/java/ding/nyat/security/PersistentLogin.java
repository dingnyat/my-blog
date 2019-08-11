package ding.nyat.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
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
}
