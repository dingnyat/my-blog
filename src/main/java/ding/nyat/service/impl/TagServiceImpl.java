package ding.nyat.service.impl;

import ding.nyat.repository.TagRepository;
import ding.nyat.service.TagService;
import ding.nyat.entity.TagEntity;
import ding.nyat.model.Tag;
import ding.nyat.service.ServiceAbstract;
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
}
