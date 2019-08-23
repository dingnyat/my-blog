package ding.nyat.service;

import ding.nyat.model.Comment;
import ding.nyat.model.Post;
import ding.nyat.util.datatable.DataTableRequest;

import java.util.List;

public interface PostService extends ServiceInterface<Integer, Post> {
    List<Post> getTableData(String username, DataTableRequest dataTableRequest, String... fieldNames);

    Long countTableDataRecords(String username, DataTableRequest dataTableRequest, String... fieldNames);

    Long countAllRecords(String username);

    Boolean isAuthor(String username, Integer postId);

    Post getByCode(String code);

    void addChildComment(Integer parentCommentId, Comment comment);

    void addComment(Integer postId, Comment comment);
}
