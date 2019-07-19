package dou.ding.nyat.blog.service;

import dou.ding.nyat.blog.model.Author;
import dou.ding.nyat.blog.model.SocialLink;

public interface AuthorService extends ServiceInterface<Integer, Author> {
    void deleteLink(Integer linkId, Integer authorId);

    void addLink(Integer authorId, SocialLink socialLink);
}
