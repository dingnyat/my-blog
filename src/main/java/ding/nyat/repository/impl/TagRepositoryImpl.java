package ding.nyat.repository.impl;

import ding.nyat.entity.TagEntity;
import ding.nyat.repository.RepositoryAbstraction;
import ding.nyat.repository.TagRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

@Repository
@Transactional
public class TagRepositoryImpl extends RepositoryAbstraction<TagEntity> implements TagRepository {
    @Override
    public TagEntity getByCode(String code) {
        try {
            return entityManager
                    .createQuery("SELECT c FROM TagEntity c WHERE c.code='" + code + "'", TagEntity.class)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
