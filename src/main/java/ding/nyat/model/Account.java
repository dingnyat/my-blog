package ding.nyat.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class Account extends CommonModel<Integer> {
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
