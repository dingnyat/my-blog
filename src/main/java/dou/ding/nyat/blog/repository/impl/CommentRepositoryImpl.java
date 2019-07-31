package dou.ding.nyat.blog.repository.impl;

import dou.ding.nyat.blog.entity.CommentEntity;
import dou.ding.nyat.blog.repository.CommentRepository;
import dou.ding.nyat.blog.repository.RepositoryAbstract;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CommentRepositoryImpl extends RepositoryAbstract<Integer, CommentEntity> implements CommentRepository {
}
