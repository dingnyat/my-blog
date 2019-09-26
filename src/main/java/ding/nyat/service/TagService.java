package ding.nyat.service;

import ding.nyat.model.Tag;

public interface TagService extends ServiceInterface<Tag> {
    Tag getByCode(String code);
}
