package dou.ding.nyat.blog.repository.impl;

import dou.ding.nyat.blog.entity.ArticleEntity;
import dou.ding.nyat.blog.repository.ArticleRepository;
import dou.ding.nyat.blog.repository.RepositoryAbstract;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ArticleRepositoryImpl extends RepositoryAbstract<Integer, ArticleEntity> implements ArticleRepository {
}
