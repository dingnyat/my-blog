package dou.ding.nyat.blog.repository.impl;

import dou.ding.nyat.blog.entity.PostEntity;
import dou.ding.nyat.blog.repository.PostRepository;
import dou.ding.nyat.blog.repository.RepositoryAbstract;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class PostRepositoryImpl extends RepositoryAbstract<Integer, PostEntity> implements PostRepository {
}
