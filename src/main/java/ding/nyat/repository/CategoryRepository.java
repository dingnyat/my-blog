package ding.nyat.repository;

import ding.nyat.entity.CategoryEntity;

public interface CategoryRepository extends RepositoryInterface<CategoryEntity> {
    CategoryEntity getByCode(String code);
}
