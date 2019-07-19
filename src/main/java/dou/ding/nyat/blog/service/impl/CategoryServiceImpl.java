package dou.ding.nyat.blog.service.impl;

import dou.ding.nyat.blog.entity.CategoryEntity;
import dou.ding.nyat.blog.entity.TagEntity;
import dou.ding.nyat.blog.model.Category;
import dou.ding.nyat.blog.model.Series;
import dou.ding.nyat.blog.model.Tag;
import dou.ding.nyat.blog.repository.CategoryRepository;
import dou.ding.nyat.blog.service.CategoryService;
import dou.ding.nyat.blog.service.ServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl extends ServiceAbstract<Integer, Category, CategoryEntity, CategoryRepository> implements CategoryService {

    @Autowired
    public CategoryServiceImpl(CategoryRepository repository) {
        super(repository);
    }

    @Override
    public Integer create(Category category) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCode(category.getCode());
        categoryEntity.setName(category.getName());
        categoryEntity.setDescription(category.getDescription());
        return repository.create(categoryEntity);
    }

    @Override
    public void update(Category model) {
        CategoryEntity entity = repository.getById(model.getId());
        entity.setCode(model.getCode());
        entity.setName(model.getName());
        entity.setDescription(model.getDescription());
        repository.update(entity);
    }

    @Override
    public Category convertToModel(CategoryEntity entity) {
        Category category = new Category();
        category.setId(entity.getId());
        category.setName(entity.getName());
        category.setCode(entity.getCode());
        category.setDescription(entity.getDescription());
        if (entity.getTags() != null) {
            category.setTags(entity.getTags().stream().map(tagEntity -> {
                Tag tag = new Tag();
                tag.setId(tagEntity.getId());
                tag.setCode(tagEntity.getCode());
                tag.setName(tagEntity.getName());
                return tag;
            }).collect(Collectors.toSet()));
        }
        if (entity.getSeries() != null) {
            category.setSeries(entity.getSeries().stream().map(seriesEntity -> {
                Series series = new Series();
                series.setId(seriesEntity.getId());
                series.setCode(seriesEntity.getCode());
                series.setName(seriesEntity.getName());
                series.setDescription(seriesEntity.getDescription());
                return series;
            }).collect(Collectors.toSet()));
        }
        return category;
    }

    @Override
    public void addTag(Integer categoryId, Tag tag) {
        CategoryEntity categoryEntity = repository.getById(categoryId);
        TagEntity tagEntity = new TagEntity();
        tagEntity.setCode(tag.getCode());
        tagEntity.setName(tag.getName());
        categoryEntity.getTags().add(tagEntity);
        repository.update(categoryEntity);
    }
}
