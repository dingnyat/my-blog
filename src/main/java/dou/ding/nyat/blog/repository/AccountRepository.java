package dou.ding.nyat.blog.repository;


import dou.ding.nyat.blog.entity.AccountEntity;

public interface AccountRepository extends RepositoryInterface<Integer, AccountEntity> {
    AccountEntity getByUsername(String username);

    AccountEntity getByEmail(String email);
}