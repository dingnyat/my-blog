package ding.nyat.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class UserPrincipal extends User {

    private String authorCode;

    public UserPrincipal(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public UserPrincipal(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public boolean hasAnyRoles(String... roles) {
        for (GrantedAuthority authority : this.getAuthorities()) {
            for (String role : roles) {
                if (authority.getAuthority().equals(role)) return true;
            }
        }
        return false;
    }
}
