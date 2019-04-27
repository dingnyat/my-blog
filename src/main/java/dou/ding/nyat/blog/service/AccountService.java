package dou.ding.nyat.blog.service;


import dou.ding.nyat.blog.model.Account;
import dou.ding.nyat.blog.model.AccountVerificationToken;
import dou.ding.nyat.blog.model.Author;

public interface AccountService extends ServiceInterface<Integer, Account> {
    void deleteByUsername(String username);

    Account getByUsername(String username);

    boolean isEmailExisted(String email);

    void createAccountVerificationToken(AccountVerificationToken verificationToken);

    AccountVerificationToken getVerificationToken(String token);

    Author getAuthorByUsername(String username);
}
