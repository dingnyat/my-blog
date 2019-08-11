package ding.nyat.repository.impl;

import ding.nyat.entity.TagEntity;
import ding.nyat.repository.RepositoryAbstract;
import ding.nyat.repository.TagRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class TagRepositoryImpl extends RepositoryAbstract<Integer, TagEntity> implements TagRepository {
}
