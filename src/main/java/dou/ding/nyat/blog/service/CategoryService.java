package dou.ding.nyat.blog.service;

import dou.ding.nyat.blog.model.Category;
import dou.ding.nyat.blog.model.Tag;

public interface CategoryService extends ServiceInterface<Integer, Category> {
    void addTag(Integer categoryId, Tag tag);
}
