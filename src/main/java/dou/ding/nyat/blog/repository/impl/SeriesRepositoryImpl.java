package dou.ding.nyat.blog.repository.impl;

import dou.ding.nyat.blog.entity.SeriesEntity;
import dou.ding.nyat.blog.repository.RepositoryAbstract;
import dou.ding.nyat.blog.repository.SeriesRepository;
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
