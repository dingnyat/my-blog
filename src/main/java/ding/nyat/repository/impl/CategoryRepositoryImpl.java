package ding.nyat.repository.impl;

import ding.nyat.entity.CategoryEntity;
import ding.nyat.repository.CategoryRepository;
import ding.nyat.repository.RepositoryAbstraction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CategoryRepositoryImpl extends RepositoryAbstraction<CategoryEntity> implements CategoryRepository {
}
