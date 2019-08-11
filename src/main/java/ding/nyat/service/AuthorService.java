package ding.nyat.service;

import ding.nyat.model.Author;
import ding.nyat.model.SocialLink;

public interface AuthorService extends ServiceInterface<Integer, Author> {
    void deleteLink(Integer linkId, Integer authorId);

    void addLink(Integer authorId, SocialLink socialLink);

    Author getByCode(String code);
}
