package ding.nyat.repository;

import ding.nyat.util.datatable.DataTableRequest;
import ding.nyat.util.search.SearchRequest;

import java.io.Serializable;
import java.util.List;

public interface RepositoryInterface<E> {
    void create(E entity);

    void update(E entity);

    void delete(E entity);

    E read(Serializable id);

    List<E> readAll();

    <S extends SearchRequest> List<E> search(S searchRequest);

    <S extends SearchRequest> int countSearchRecords(S searchRequest);

    List<E> getTableData(DataTableRequest dataTableRequest);

    int countFilteredTableData(DataTableRequest dataTableRequest);

    int countAllRecords();
}
