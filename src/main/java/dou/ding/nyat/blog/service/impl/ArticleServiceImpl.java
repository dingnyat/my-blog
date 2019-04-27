package dou.ding.nyat.blog.service.impl;

import dou.ding.nyat.blog.entity.ArticleEntity;
import dou.ding.nyat.blog.model.Article;
import dou.ding.nyat.blog.repository.ArticleRepository;
import dou.ding.nyat.blog.service.ArticleService;
import dou.ding.nyat.blog.service.ServiceAbstract;
import dou.ding.nyat.blog.util.BeanTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ArticleServiceImpl extends ServiceAbstract<Integer, Article, ArticleEntity, ArticleRepository> implements ArticleService {

    @Autowired
    public ArticleServiceImpl(ArticleRepository repository, BeanTools beanTools) {
        super(repository, beanTools);
    }
}
