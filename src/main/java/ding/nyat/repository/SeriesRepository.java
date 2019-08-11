package ding.nyat.repository;

import ding.nyat.entity.SeriesEntity;

public interface SeriesRepository extends RepositoryInterface<Integer, SeriesEntity> {
    SeriesEntity getByCode(String code);
}
