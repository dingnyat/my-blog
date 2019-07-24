package dou.ding.nyat.blog.repository;

import dou.ding.nyat.blog.entity.PostEntity;
import dou.ding.nyat.blog.util.datatable.DataTableRequest;

import java.util.List;

public interface PostRepository extends RepositoryInterface<Integer, PostEntity> {
    List<PostEntity> getTableData(String username, DataTableRequest dataTableRequest, String... fieldNames);

    Long countTableDataRecords(String username, DataTableRequest dataTableRequest, String... fieldNames);

    Long countAllRecords(String username);
}
