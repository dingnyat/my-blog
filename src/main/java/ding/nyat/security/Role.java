package ding.nyat.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role")
public class Role {

    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false, insertable = false)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true, updatable = false, insertable = false)
    private String name;

    @Transient
    public static final Role ADMIN = new Role(1, "ADMIN");

    @Transient
    public static final Role AUTHOR = new Role(2, "AUTHOR");

    public String getFullName() {
        return "ROLE_" + name;
    }
}
