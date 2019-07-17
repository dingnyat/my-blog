package dou.ding.nyat.blog.security;


import dou.ding.nyat.blog.entity.AccountEntity;
import dou.ding.nyat.blog.entity.RoleEntity;
import dou.ding.nyat.blog.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Autowired
    private AccountRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountEntity accountEntity = repository.getByUsername(username);
        if (accountEntity != null) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            Set<RoleEntity> roles = accountEntity.getRoles();
            roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
            return new User(accountEntity.getUsername(), accountEntity.getPassword(),
                    accountEntity.isActived(), true, true, true, authorities);
        } else {
            throw new UsernameNotFoundException("Account isn't existed!");
        }
    }
}
