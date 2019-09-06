package ding.nyat.service;

import ding.nyat.model.Comment;
import ding.nyat.model.Post;

public interface PostService extends ServiceInterface<Post> {
    boolean checkOwnership(String username, Integer postId);

    Post getByCode(String code);

    void addChildComment(Integer parentCommentId, Comment comment);

    void addComment(Integer postId, Comment comment);
}
