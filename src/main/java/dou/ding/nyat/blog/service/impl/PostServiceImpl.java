package dou.ding.nyat.blog.service.impl;

import dou.ding.nyat.blog.entity.PostEntity;
import dou.ding.nyat.blog.model.Post;
import dou.ding.nyat.blog.repository.PostRepository;
import dou.ding.nyat.blog.service.PostService;
import dou.ding.nyat.blog.service.ServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostServiceImpl extends ServiceAbstract<Integer, Post, PostEntity, PostRepository> implements PostService {

    @Autowired
    public PostServiceImpl(PostRepository repository) {
        super(repository);
    }

    @Override
    public Integer create(Post model) {
        return null;
    }

    @Override
    public void update(Post model) {

    }

    @Override
    public Post convertToModel(PostEntity entity) {
        return null;
    }
}
