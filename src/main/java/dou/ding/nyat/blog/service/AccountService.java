package dou.ding.nyat.blog.service;


import dou.ding.nyat.blog.model.Account;

public interface AccountService extends ServiceInterface<Integer, Account> {
    void deleteByUsername(String username);

    Account getByUsername(String username);

    Account getByAuthorId(Integer id);
}
