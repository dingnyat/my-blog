package ding.nyat.repository;

import ding.nyat.entity.TagEntity;

public interface TagRepository extends RepositoryInterface<TagEntity> {
    TagEntity getByCode(String code);
}
