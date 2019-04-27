package dou.ding.nyat.blog.repository.impl;

import dou.ding.nyat.blog.entity.AuthorEntity;
import dou.ding.nyat.blog.repository.AuthorRepository;
import dou.ding.nyat.blog.repository.RepositoryAbstract;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class AuthorRepositoryImpl extends RepositoryAbstract<Integer, AuthorEntity> implements AuthorRepository {
}
