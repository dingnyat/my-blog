package ding.nyat.service.impl;

import ding.nyat.entity.TagEntity;
import ding.nyat.model.Tag;
import ding.nyat.repository.TagRepository;
import ding.nyat.service.ServiceAbstraction;
import ding.nyat.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TagServiceImpl extends ServiceAbstraction<Tag, TagEntity, TagRepository> implements TagService {

    private TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository repository) {
        super(repository);
        this.tagRepository = repository;
    }

    @Override
    public Tag getByCode(String code) {
        TagEntity tagEntity = tagRepository.getByCode(code);
        if (tagEntity != null) return convertToModel(tagEntity);
        return null;
    }
}
