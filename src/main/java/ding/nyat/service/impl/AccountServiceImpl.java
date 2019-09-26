package ding.nyat.service.impl;

import ding.nyat.entity.AccountEntity;
import ding.nyat.entity.AuthorEntity;
import ding.nyat.model.Account;
import ding.nyat.repository.AccountRepository;
import ding.nyat.security.Role;
import ding.nyat.service.AccountService;
import ding.nyat.service.ServiceAbstraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;

@Service
@Transactional
public class AccountServiceImpl extends ServiceAbstraction<Account, AccountEntity, AccountRepository> implements AccountService {

    public AccountServiceImpl(@Autowired AccountRepository repository) {
        super(repository);
    }

    @Override
    public void create(Account account) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUsername(account.getUsername());
        accountEntity.setPassword(account.getPassword());
        accountEntity.setActive(account.isActived());
        accountEntity.setEmail(account.getEmail());

        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setName(account.getAuthor().getName());
        authorEntity.setCode(account.getAuthor().getCode());
        authorEntity.setDescription(account.getAuthor().getDescription());
        if (account.getAuthor().getAvatarUrl() != null && !account.getAuthor().getAvatarUrl().isEmpty())
            authorEntity.setAvatarUrl(account.getAuthor().getAvatarUrl());
        authorEntity.setAccount(accountEntity); // nhớ set ngược lại account, thì mới cascade được

        accountEntity.setAuthor(authorEntity);
        accountEntity.setRoles(new HashSet<>(Collections.singletonList(Role.AUTHOR)));
        repository.create(accountEntity);
    }

    @Override
    public void update(Account account) {
        AccountEntity accountEntity = repository.read(account.getId());
        if (account.getPassword() != null && !account.getPassword().isEmpty())
            accountEntity.setPassword(account.getPassword());
        accountEntity.setEmail(account.getEmail());
        repository.update(accountEntity);
    }

    @Override
    public void deleteByUsername(String username) {
        repository.delete(repository.getByUsername(username));
    }

    @Override
    public Account getByUsername(String username) {
        AccountEntity accountEntity = repository.getByUsername(username);
        if (accountEntity == null) return null;
        return convertToModel(accountEntity);
    }

    @Override
    public Account getByAuthorId(Integer id) {
        // lười viết xuống repo hay dùng authorRepo. dù sao account cũng ít record với hiếm khi dùng method này
        return readAll().stream()
                .filter(account -> account.getAuthor() != null && account.getAuthor().getId().equals(id))
                .findFirst().orElse(null);
    }
}
