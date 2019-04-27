package dou.ding.nyat.blog.service.impl;


import dou.ding.nyat.blog.entity.AccountEntity;
import dou.ding.nyat.blog.entity.AccountVerificationTokenEntity;
import dou.ding.nyat.blog.model.Account;
import dou.ding.nyat.blog.model.AccountVerificationToken;
import dou.ding.nyat.blog.model.Author;
import dou.ding.nyat.blog.repository.AccountRepository;
import dou.ding.nyat.blog.repository.AccountVerificationTokenRepository;
import dou.ding.nyat.blog.service.AccountService;
import dou.ding.nyat.blog.service.ServiceAbstract;
import dou.ding.nyat.blog.util.BeanTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountServiceImpl extends ServiceAbstract<Integer, Account, AccountEntity, AccountRepository> implements AccountService {

    @Autowired
    private AccountVerificationTokenRepository verificationTokenRepository;

    public AccountServiceImpl(@Autowired AccountRepository repository, @Autowired BeanTools beanTools) {
        super(repository, beanTools);
    }

    @Override
    public void deleteByUsername(String username) {
        repository.delete(repository.getByUsername(username));
    }

    @Override
    public Account getByUsername(String username) {
        AccountEntity accountEntity = repository.getByUsername(username);
        if (accountEntity == null) return null;
        return beanTools.map(accountEntity, new Account());
    }

    @Override
    public boolean isEmailExisted(String email) {
        return repository.getByEmail(email) != null;
    }

    @Override
    public void createAccountVerificationToken(AccountVerificationToken verificationToken) {
        verificationTokenRepository.create(this.beanTools.map(verificationToken, new AccountVerificationTokenEntity()));
    }

    @Override
    public AccountVerificationToken getVerificationToken(String token) {
        AccountVerificationTokenEntity verificationTokenEntity = verificationTokenRepository.getByToken(token);
        if (verificationTokenEntity == null) return null;
        return beanTools.map(verificationTokenEntity, new AccountVerificationToken());
    }

    @Override
    public Author getAuthorByUsername(String username) {
        AccountEntity accountEntity = repository.getByUsername(username);
        if (accountEntity == null) return null;
        return beanTools.map(accountEntity.getAuthor(), new Author());
    }
}
