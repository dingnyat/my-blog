package dou.ding.nyat.blog.service.impl;

import dou.ding.nyat.blog.entity.AuthorEntity;
import dou.ding.nyat.blog.entity.SocialLinkEntity;
import dou.ding.nyat.blog.model.Author;
import dou.ding.nyat.blog.model.SocialLink;
import dou.ding.nyat.blog.repository.AuthorRepository;
import dou.ding.nyat.blog.service.AuthorService;
import dou.ding.nyat.blog.service.ServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional
public class AuthorServiceImpl extends ServiceAbstract<Integer, Author, AuthorEntity, AuthorRepository> implements AuthorService {

    @Autowired
    public AuthorServiceImpl(AuthorRepository repository) {
        super(repository);
    }

    @Override
    public Integer create(Author model) {
        return null;
    }

    @Override
    public void update(Author author) {
        AuthorEntity authorEntity = repository.getById(author.getId());
        authorEntity.setName(author.getName());
        authorEntity.setCode(author.getCode());
        authorEntity.setDescription(author.getDescription());
        if (author.getAvatarUrl() != null && !author.getAvatarUrl().isEmpty())
            authorEntity.setAvatarUrl(author.getAvatarUrl());
        repository.update(authorEntity);
    }

    @Override
    public Author convertToModel(AuthorEntity entity) {
        Author author = new Author();
        author.setId(entity.getId());
        author.setCode(entity.getCode());
        author.setName(entity.getName());
        author.setDescription(entity.getDescription());
        author.setAvatarUrl(entity.getAvatarUrl());
        author.setSocialLinks(entity.getSocialLinks().stream().map(socialLinkEntity -> {
            SocialLink socialLink = new SocialLink();
            socialLink.setId(socialLinkEntity.getId());
            socialLink.setName(socialLinkEntity.getName());
            socialLink.setLink(socialLinkEntity.getLink());
            return socialLink;
        }).collect(Collectors.toSet()));
        return author;
    }

    @Override
    public void deleteLink(Integer linkId, Integer authorId) {
        AuthorEntity authorEntity = repository.getById(authorId);
        if (authorEntity == null) return;
        authorEntity.getSocialLinks().removeIf(scl -> scl.getId() == linkId);
        repository.update(authorEntity);
    }

    @Override
    public void addLink(Integer authorId, SocialLink socialLink) {
        AuthorEntity authorEntity = repository.getById(authorId);
        if (authorEntity == null) return;
        SocialLinkEntity socialLinkEntity = new SocialLinkEntity();
        socialLinkEntity.setName(socialLink.getName());
        socialLinkEntity.setLink(socialLink.getLink());
        authorEntity.getSocialLinks().add(socialLinkEntity);
        repository.update(authorEntity);
    }
}