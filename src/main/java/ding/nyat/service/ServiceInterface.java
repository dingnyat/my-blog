package ding.nyat.service;

import ding.nyat.util.datatable.DataTableRequest;
import ding.nyat.util.search.SearchRequest;

import java.io.Serializable;
import java.util.List;

public interface ServiceInterface<M> {
    void create(M model);

    void update(M model) throws IllegalAccessException;

    void delete(Serializable id);

    M read(Serializable id);

    List<M> readAll();

    <S extends SearchRequest> List<M> search(S searchRequest);

    <S extends SearchRequest> int countSearchRecords(S searchRequest);

    List<M> getTableData(DataTableRequest dataTableRequest);

    int countTableDataRecords(DataTableRequest dataTableRequest);

    int countAllRecords();
}
