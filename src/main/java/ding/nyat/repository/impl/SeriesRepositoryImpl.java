package ding.nyat.repository.impl;

import ding.nyat.entity.SeriesEntity;
import ding.nyat.repository.RepositoryAbstract;
import ding.nyat.repository.SeriesRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class SeriesRepositoryImpl extends RepositoryAbstract<Integer, SeriesEntity> implements SeriesRepository {
    @Override
    public SeriesEntity getByCode(String code) {
        return entityManager.createQuery("SELECT s FROM SeriesEntity s WHERE s.code='" + code + "'", SeriesEntity.class).getSingleResult();
    }
}
