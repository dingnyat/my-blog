package ding.nyat.repository.impl;

import ding.nyat.entity.CommentEntity;
import ding.nyat.repository.CommentRepository;
import ding.nyat.repository.RepositoryAbstract;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CommentRepositoryImpl extends RepositoryAbstract<Integer, CommentEntity> implements CommentRepository {
}
