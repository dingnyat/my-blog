package ding.nyat.repository;


import ding.nyat.entity.AccountEntity;

public interface AccountRepository extends RepositoryInterface<Integer, AccountEntity> {
    AccountEntity getByUsername(String username);

    AccountEntity getByEmail(String email);
}
