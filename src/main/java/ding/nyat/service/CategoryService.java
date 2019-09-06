package ding.nyat.service;

import ding.nyat.model.Category;
import ding.nyat.model.Tag;

public interface CategoryService extends ServiceInterface<Category> {
    void addTag(Integer categoryId, Tag tag);

    void deselectSeries(Integer seriesId, Integer categoryId);

    void linkSeries(Integer seriesId, Integer categoryId);
}
