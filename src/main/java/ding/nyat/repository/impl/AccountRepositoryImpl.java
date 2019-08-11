package ding.nyat.repository.impl;

import ding.nyat.entity.AccountEntity;
import ding.nyat.repository.AccountRepository;
import ding.nyat.repository.RepositoryAbstract;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

@Repository
@Transactional
public class AccountRepositoryImpl extends RepositoryAbstract<Integer, AccountEntity> implements AccountRepository {

    @Override
    public AccountEntity getByUsername(String username) {
        try {
            entityManager.setFlushMode(FlushModeType.COMMIT); // lien quan den AditorAware
            AccountEntity accountEntity = this.entityManager
                    .createQuery("SELECT a FROM AccountEntity a WHERE a.username = :username", AccountEntity.class)
                    .setParameter("username", username).getSingleResult();
            entityManager.setFlushMode(FlushModeType.AUTO);
            return accountEntity;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public AccountEntity getByEmail(String email) {
        try {
            return this.entityManager
                    .createQuery("SELECT a FROM AccountEntity a WHERE a.email = :email", AccountEntity.class)
                    .setParameter("email", email).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
