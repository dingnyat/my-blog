package ding.nyat.service;


import ding.nyat.model.Account;

public interface AccountService extends ServiceInterface<Account> {
    void deleteByUsername(String username);

    Account getByUsername(String username);

    Account getByAuthorId(Integer id);
}
