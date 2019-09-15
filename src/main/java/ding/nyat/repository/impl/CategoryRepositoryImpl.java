package ding.nyat.repository.impl;

import ding.nyat.entity.CategoryEntity;
import ding.nyat.repository.CategoryRepository;
import ding.nyat.repository.RepositoryAbstraction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

@Repository
@Transactional
public class CategoryRepositoryImpl extends RepositoryAbstraction<CategoryEntity> implements CategoryRepository {
    @Override
    public CategoryEntity getByCode(String code) {
        try {
            return entityManager
                    .createQuery("SELECT c FROM CategoryEntity c WHERE c.code='" + code + "'", CategoryEntity.class)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
