package ding.nyat.repository.impl;

import ding.nyat.repository.RepositoryAbstract;
import ding.nyat.entity.AuthorEntity;
import ding.nyat.repository.AuthorRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

@Repository
@Transactional
public class AuthorRepositoryImpl extends RepositoryAbstract<Integer, AuthorEntity> implements AuthorRepository {
    @Override
    public AuthorEntity getByCode(String code) {
        try {
            return entityManager.createQuery("SELECT a FROM AuthorEntity a WHERE a.code='" + code + "'", AuthorEntity.class).getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }
}
