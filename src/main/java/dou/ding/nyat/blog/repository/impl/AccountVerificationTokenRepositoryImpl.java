package dou.ding.nyat.blog.repository.impl;

import dou.ding.nyat.blog.entity.AccountEntity;
import dou.ding.nyat.blog.entity.AccountVerificationTokenEntity;
import dou.ding.nyat.blog.repository.AccountVerificationTokenRepository;
import dou.ding.nyat.blog.repository.RepositoryAbstract;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

@Repository
@Transactional
public class AccountVerificationTokenRepositoryImpl extends RepositoryAbstract<Integer, AccountVerificationTokenEntity> implements AccountVerificationTokenRepository {
    @Override
    public AccountVerificationTokenEntity getByToken(String token) {
        try {
            return this.entityManager
                    .createQuery("SELECT a FROM AccountVerificationTokenEntity a WHERE a.token = :token", AccountVerificationTokenEntity.class)
                    .setParameter("token", token).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public AccountVerificationTokenEntity getByAccount(AccountEntity accountEntity) {
        try {
            return this.entityManager
                    .createQuery("SELECT a FROM AccountVerificationTokenEntity a WHERE a.account = :account", AccountVerificationTokenEntity.class)
                    .setParameter("account", accountEntity).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
