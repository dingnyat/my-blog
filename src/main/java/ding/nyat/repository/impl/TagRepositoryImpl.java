package ding.nyat.repository.impl;

import ding.nyat.entity.TagEntity;
import ding.nyat.repository.RepositoryAbstraction;
import ding.nyat.repository.TagRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class TagRepositoryImpl extends RepositoryAbstraction<TagEntity> implements TagRepository {
}
