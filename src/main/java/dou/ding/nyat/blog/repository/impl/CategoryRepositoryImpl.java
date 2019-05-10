package dou.ding.nyat.blog.repository.impl;

import dou.ding.nyat.blog.entity.CategoryEntity;
import dou.ding.nyat.blog.repository.CategoryRepository;
import dou.ding.nyat.blog.repository.RepositoryAbstract;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CategoryRepositoryImpl extends RepositoryAbstract<Integer, CategoryEntity> implements CategoryRepository {
}
