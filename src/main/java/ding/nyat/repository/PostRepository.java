package ding.nyat.repository;

import ding.nyat.entity.PostEntity;

public interface PostRepository extends RepositoryInterface<PostEntity> {
    PostEntity getByCode(String code);
}
