package ding.nyat.service.impl;

import ding.nyat.entity.CategoryEntity;
import ding.nyat.entity.SeriesEntity;
import ding.nyat.entity.TagEntity;
import ding.nyat.model.Category;
import ding.nyat.model.Tag;
import ding.nyat.repository.CategoryRepository;
import ding.nyat.repository.SeriesRepository;
import ding.nyat.service.CategoryService;
import ding.nyat.service.ServiceAbstraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
public class CategoryServiceImpl extends ServiceAbstraction<Category, CategoryEntity, CategoryRepository> implements CategoryService {

    @Autowired
    private SeriesRepository seriesRepository;

    private CategoryRepository repository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public void update(Category model) {
        CategoryEntity entity = repository.read(model.getId());
        entity.setCode(model.getCode());
        entity.setName(model.getName());
        entity.setDescription(model.getDescription());
        repository.update(entity);
    }

    @Override
    public void addTag(Integer categoryId, Tag tag) {
        CategoryEntity categoryEntity = repository.read(categoryId);
        TagEntity tagEntity = new TagEntity();
        tagEntity.setCode(tag.getCode());
        tagEntity.setName(tag.getName());
        tagEntity.setCategory(categoryEntity);
        categoryEntity.getTags().add(tagEntity);
        repository.update(categoryEntity);
    }

    @Override
    public void deselectSeries(Integer seriesId, Integer categoryId) {
        CategoryEntity entity = repository.read(categoryId);
        if (entity == null) return;
        entity.getSeries().removeIf(seriesEntity -> Objects.equals(seriesEntity.getId(), seriesId));
        repository.update(entity);
    }

    @Override
    public void linkSeries(Integer seriesId, Integer categoryId) {
        CategoryEntity entity = repository.read(categoryId);
        SeriesEntity seriesEntity = seriesRepository.read(seriesId);
        if (entity == null) return;
        entity.getSeries().add(seriesEntity);
        repository.update(entity);
    }

    @Override
    public Category getByCode(String code) {
        CategoryEntity categoryEntity = repository.getByCode(code);
        if (categoryEntity != null) return convertToModel(categoryEntity);
        return null;
    }
}
