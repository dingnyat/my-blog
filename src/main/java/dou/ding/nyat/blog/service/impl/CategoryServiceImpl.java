package dou.ding.nyat.blog.service.impl;

import dou.ding.nyat.blog.entity.CategoryEntity;
import dou.ding.nyat.blog.model.Category;
import dou.ding.nyat.blog.repository.CategoryRepository;
import dou.ding.nyat.blog.service.CategoryService;
import dou.ding.nyat.blog.service.ServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryServiceImpl extends ServiceAbstract<Integer, Category, CategoryEntity, CategoryRepository> implements CategoryService {

    @Autowired
    public CategoryServiceImpl(CategoryRepository repository) {
        super(repository);
    }

    @Override
    public Integer create(Category model) {
        return null;
    }

    @Override
    public void update(Category model) {

    }

    @Override
    public Category convertToModel(CategoryEntity entity) {
        return null;
    }
}
