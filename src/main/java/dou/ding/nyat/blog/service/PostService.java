package dou.ding.nyat.blog.service;

import dou.ding.nyat.blog.model.Post;
import dou.ding.nyat.blog.util.datatable.DataTableRequest;

import java.util.List;

public interface PostService extends ServiceInterface<Integer, Post> {
    List<Post> getTableData(String username, DataTableRequest dataTableRequest, String... fieldNames);

    Long countTableDataRecords(String username, DataTableRequest dataTableRequest, String... fieldNames);

    Long countAllRecords(String username);

    Boolean isAuthor(String username, Integer postId);
}
