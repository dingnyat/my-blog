package ding.nyat.service.impl;

import ding.nyat.entity.CategoryEntity;
import ding.nyat.entity.SeriesEntity;
import ding.nyat.entity.TagEntity;
import ding.nyat.model.Category;
import ding.nyat.model.Tag;
import ding.nyat.repository.CategoryRepository;
import ding.nyat.repository.SeriesRepository;
import ding.nyat.service.CategoryService;
import ding.nyat.service.ServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryServiceImpl extends ServiceAbstract<Integer, Category, CategoryEntity, CategoryRepository> implements CategoryService {

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository repository) {
        super(repository);
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
    public void addTag(Integer categoryId, Tag tag) {
        CategoryEntity categoryEntity = repository.getById(categoryId);
        TagEntity tagEntity = new TagEntity();
        tagEntity.setCode(tag.getCode());
        tagEntity.setName(tag.getName());
        categoryEntity.getTags().add(tagEntity);
        repository.update(categoryEntity);
    }

    @Override
    public void deselectSeries(Integer seriesId, Integer categoryId) {
        CategoryEntity entity = repository.getById(categoryId);
        if (entity == null) return;
        entity.getSeries().removeIf(seriesEntity -> seriesEntity.getId() == seriesId);
        repository.update(entity);
    }

    @Override
    public void linkSeries(Integer seriesId, Integer categoryId) {
        CategoryEntity entity = repository.getById(categoryId);
        SeriesEntity seriesEntity = seriesRepository.getById(seriesId);
        if (entity == null) return;
        entity.getSeries().add(seriesEntity);
        repository.update(entity);
    }
}
