package ding.nyat.service;

import ding.nyat.util.datatable.DataTableRequest;
import ding.nyat.util.datatable.DataTableResponse;
import ding.nyat.util.search.SearchRequest;
import ding.nyat.util.search.SearchResponse;

import java.io.Serializable;
import java.util.List;

public interface ServiceInterface<M> {
    void create(M model);

    void update(M model) throws IllegalAccessException;

    void delete(Serializable id);

    M read(Serializable id);

    List<M> readAll();

    SearchResponse<M> search(SearchRequest searchRequest);

    DataTableResponse<M> getTableData(DataTableRequest dataTableRequest);

    int countAllRecords();
}
