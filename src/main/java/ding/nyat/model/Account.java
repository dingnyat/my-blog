package ding.nyat.model;

import ding.nyat.annotation.Identifier;
import ding.nyat.security.Role;
import lombok.Data;

import java.util.Set;

@Data
public class Account {
    @Identifier
    private Integer id;
    private String username;
    private String password;
    private String email;
    private boolean isActived;
    private Set<Role> roles;
    private Author author;

    public Account() {
        this.isActived = false;
    }
}
