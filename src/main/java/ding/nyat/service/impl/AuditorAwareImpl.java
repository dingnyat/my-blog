package ding.nyat.service.impl;

import ding.nyat.repository.AccountRepository;
import ding.nyat.entity.AuthorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component(value = "auditorAware")
public class AuditorAwareImpl implements AuditorAware<AuthorEntity> {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Optional<AuthorEntity> getCurrentAuditor() {
        if (SecurityContextHolder.getContext().getAuthentication() != null && !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            AuthorEntity authorEntity = accountRepository.getByUsername(user.getUsername()).getAuthor();
            return Optional.of(authorEntity);
        }
        return Optional.empty();
    }
}
