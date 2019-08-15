package ding.nyat.security;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false, insertable = false)
    private int id;

    @Column(name = "name", nullable = false, unique = true, updatable = false, insertable = false)
    private String name;

    @Transient
    public static final Role ADMIN = new Role(1, "ADMIN");

    @Transient
    public static final Role AUTHOR = new Role(2, "AUTHOR");

    public Role() {
    }

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getFullName() {
        return "ROLE_" + name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
