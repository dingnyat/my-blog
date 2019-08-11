package ding.nyat.repository;

import ding.nyat.entity.PostEntity;
import ding.nyat.util.datatable.DataTableRequest;

import java.util.List;

public interface PostRepository extends RepositoryInterface<Integer, PostEntity> {
    List<PostEntity> getTableData(String username, DataTableRequest dataTableRequest, String... fieldNames);

    Long countTableDataRecords(String username, DataTableRequest dataTableRequest, String... fieldNames);

    Long countAllRecords(String username);

    PostEntity getByCode(String code);
}
