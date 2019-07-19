package dou.ding.nyat.blog.service.impl;

import dou.ding.nyat.blog.entity.TagEntity;
import dou.ding.nyat.blog.model.Tag;
import dou.ding.nyat.blog.repository.TagRepository;
import dou.ding.nyat.blog.service.ServiceAbstract;
import dou.ding.nyat.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TagServiceImpl extends ServiceAbstract<Integer, Tag, TagEntity, TagRepository> implements TagService {
    @Autowired
    public TagServiceImpl(TagRepository repository) {
        super(repository);
    }

    @Override
    public Integer create(Tag model) {
        return null;
    }

    @Override
    public void update(Tag model) {

    }

    @Override
    public Tag convertToModel(TagEntity entity) {
        return null;
    }
}
