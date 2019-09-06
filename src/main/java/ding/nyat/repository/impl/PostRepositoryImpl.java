package ding.nyat.repository.impl;

import ding.nyat.entity.PostEntity;
import ding.nyat.repository.PostRepository;
import ding.nyat.repository.RepositoryAbstraction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

@Repository
@Transactional
public class PostRepositoryImpl extends RepositoryAbstraction<PostEntity> implements PostRepository {
    @Override
    public PostEntity getByCode(String code) {
        try {
            return entityManager
                    .createQuery("SELECT p FROM PostEntity p WHERE p.code='" + code + "'", PostEntity.class)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }


}
