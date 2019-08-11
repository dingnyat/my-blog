package ding.nyat.service.impl;

import ding.nyat.entity.AuthorEntity;
import ding.nyat.entity.SocialLinkEntity;
import ding.nyat.model.Author;
import ding.nyat.model.SocialLink;
import ding.nyat.repository.AuthorRepository;
import ding.nyat.service.AuthorService;
import ding.nyat.service.ServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthorServiceImpl extends ServiceAbstract<Integer, Author, AuthorEntity, AuthorRepository> implements AuthorService {

    @Autowired
    public AuthorServiceImpl(AuthorRepository repository) {
        super(repository);
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

    @Override
    public Author getByCode(String code) {
        AuthorEntity authorEntity = repository.getByCode(code);
        return authorEntity == null ? null : convertToModel(repository.getByCode(code));
    }
}
