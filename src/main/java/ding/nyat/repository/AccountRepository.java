package ding.nyat.repository;


import ding.nyat.entity.AccountEntity;

public interface AccountRepository extends RepositoryInterface<AccountEntity> {
    AccountEntity getByUsername(String username);

    AccountEntity getByEmail(String email);
}
