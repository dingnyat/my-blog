package dou.ding.nyat.blog.service.impl;

import dou.ding.nyat.blog.entity.AuthorEntity;
import dou.ding.nyat.blog.model.Author;
import dou.ding.nyat.blog.repository.AuthorRepository;
import dou.ding.nyat.blog.service.AuthorService;
import dou.ding.nyat.blog.service.ServiceAbstract;
import dou.ding.nyat.blog.util.BeanTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthorServiceImpl extends ServiceAbstract<Integer, Author, AuthorEntity, AuthorRepository> implements AuthorService {
    @Autowired
    public AuthorServiceImpl(AuthorRepository repository, BeanTools beanTools) {
        super(repository, beanTools);
    }
}