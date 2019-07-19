package dou.ding.nyat.blog.service.impl;

import dou.ding.nyat.blog.entity.AccountEntity;
import dou.ding.nyat.blog.entity.AuthorEntity;
import dou.ding.nyat.blog.model.Account;
import dou.ding.nyat.blog.model.Author;
import dou.ding.nyat.blog.model.Role;
import dou.ding.nyat.blog.model.SocialLink;
import dou.ding.nyat.blog.repository.AccountRepository;
import dou.ding.nyat.blog.repository.RoleRepository;
import dou.ding.nyat.blog.service.AccountService;
import dou.ding.nyat.blog.service.ServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountServiceImpl extends ServiceAbstract<Integer, Account, AccountEntity, AccountRepository> implements AccountService {

    @Autowired
    private RoleRepository roleRepository;

    public AccountServiceImpl(@Autowired AccountRepository repository) {
        super(repository);
    }

    @Override
    public Integer create(Account account) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUsername(account.getUsername());
        accountEntity.setPassword(account.getPassword());
        accountEntity.setActived(account.isActived());
        accountEntity.setEmail(account.getEmail());

        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setName(account.getAuthor().getName());
        authorEntity.setCode(account.getAuthor().getCode());
        authorEntity.setDescription(account.getAuthor().getDescription());
        if (account.getAuthor().getAvatarUrl() != null && !account.getAuthor().getAvatarUrl().isEmpty())
            authorEntity.setAvatarUrl(account.getAuthor().getAvatarUrl());
        authorEntity.setAccount(accountEntity); // nhớ set ngược lại account, thì mới cascade được

        accountEntity.setAuthor(authorEntity);
        accountEntity.setRoles(new HashSet<>(Collections.singletonList(roleRepository.getById(2))));
        return repository.create(accountEntity);
    }

    @Override
    public void update(Account account) {
        AccountEntity accountEntity = repository.getById(account.getId());
        if (account.getPassword() != null && !account.getPassword().isEmpty())
            accountEntity.setPassword(account.getPassword());
        accountEntity.setEmail(account.getEmail());
        repository.update(accountEntity);
    }

    @Override
    public Account convertToModel(AccountEntity entity) {
        Account account = new Account();
        account.setId(entity.getId());
        account.setUsername(entity.getUsername());
        account.setPassword(entity.getPassword());
        account.setActived(entity.isActived());
        account.setEmail(entity.getEmail());
        account.setRoles(entity.getRoles().stream().map(roleEntity -> {
            Role role = new Role();
            role.setId(roleEntity.getId());
            role.setName(roleEntity.getName());
            return role;
        }).collect(Collectors.toSet()));
        Author author = new Author();
        author.setId(entity.getAuthor().getId());
        author.setCode(entity.getAuthor().getCode());
        author.setName(entity.getAuthor().getName());
        author.setDescription(entity.getAuthor().getDescription());
        author.setAvatarUrl(entity.getAuthor().getAvatarUrl());
        author.setSocialLinks(entity.getAuthor().getSocialLinks().stream().map(socialLinkEntity -> {
            SocialLink socialLink = new SocialLink();
            socialLink.setId(socialLinkEntity.getId());
            socialLink.setName(socialLinkEntity.getName());
            socialLink.setLink(socialLinkEntity.getLink());
            return socialLink;
        }).collect(Collectors.toSet()));
        account.setAuthor(author);
        return account;
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
        // lười viết xuống repo. dù sao account cũng ít record với hiếm khi dùng method này
        List<Account> accounts = getAllRecords();
        for (Account account : accounts) {
            if (account.getAuthor().getId().equals(id)) return account;
        }
        return null;
    }
}
