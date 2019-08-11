package ding.nyat.repository;

import ding.nyat.entity.AuthorEntity;

public interface AuthorRepository extends RepositoryInterface<Integer, AuthorEntity> {
    AuthorEntity getByCode(String code);
}
