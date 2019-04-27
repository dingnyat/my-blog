package dou.ding.nyat.blog.repository;


import dou.ding.nyat.blog.entity.AccountEntity;
import dou.ding.nyat.blog.entity.AccountVerificationTokenEntity;

public interface AccountVerificationTokenRepository extends RepositoryInterface<Integer, AccountVerificationTokenEntity> {
    AccountVerificationTokenEntity getByToken(String token);

    AccountVerificationTokenEntity getByAccount(AccountEntity accountEntity);
}
