package dou.ding.nyat.blog.repository.impl;

import dou.ding.nyat.blog.entity.TagEntity;
import dou.ding.nyat.blog.repository.RepositoryAbstract;
import dou.ding.nyat.blog.repository.TagRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class TagRepositoryImpl extends RepositoryAbstract<Integer, TagEntity> implements TagRepository {
}
