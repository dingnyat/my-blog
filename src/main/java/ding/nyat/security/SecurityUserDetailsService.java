package ding.nyat.security;


import ding.nyat.entity.AccountEntity;
import ding.nyat.entity.RoleEntity;
import ding.nyat.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service(value = "CustomUserDetailsService")
@Transactional(readOnly = true)
public class SecurityUserDetailsService implements UserDetailsService {

    public SecurityUserDetailsService() {
        this.sessionRegistry = new SessionRegistryImpl();
    }

    private SessionRegistry sessionRegistry;

    @Autowired
    private AccountRepository repository;

    @Override
    public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountEntity accountEntity = repository.getByUsername(username);
        if (accountEntity != null) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            Set<RoleEntity> roles = accountEntity.getRoles();
            roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
            return new UserPrincipal(accountEntity.getUsername(), accountEntity.getPassword(),
                    accountEntity.isActive(), true, true, true, authorities);
        } else {
            throw new UsernameNotFoundException("Account isn't existed!");
        }
    }

    public void expireUserSession(String username) {
        User user = (User) sessionRegistry.getAllPrincipals().stream().filter(principal -> principal instanceof User && ((User) principal).getUsername().equals(username)).findFirst().orElse(null);
        if (user != null) sessionRegistry.getAllSessions(user, true).forEach(SessionInformation::expireNow);
    }
}
