package dou.ding.nyat.blog.model;

import lombok.Data;

import java.util.Set;

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
