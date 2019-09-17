package ding.nyat.repository.impl;

import ding.nyat.entity.SeriesEntity;
import ding.nyat.repository.RepositoryAbstraction;
import ding.nyat.repository.SeriesRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

@Repository
@Transactional
public class SeriesRepositoryImpl extends RepositoryAbstraction<SeriesEntity> implements SeriesRepository {
    @Override
    public SeriesEntity getByCode(String code) {
        try {
            return entityManager
                    .createQuery("SELECT s FROM SeriesEntity s WHERE s.code='" + code + "'", SeriesEntity.class)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
