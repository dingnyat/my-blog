package ding.nyat.service;

import ding.nyat.util.datatable.DataTableRequest;
import ding.nyat.util.search.SearchCriteria;

import java.io.Serializable;
import java.util.List;

public interface ServiceInterface<PrimaryKeyType extends Serializable, M> {
    PrimaryKeyType create(M model);

    void update(M model);

    void delete(PrimaryKeyType id);

    M getById(PrimaryKeyType id);

    List<M> getAllRecords();

    List<M> search(List<SearchCriteria> searchCriteria);

    Long countSearchedRecords(List<SearchCriteria> searchCriteria);

    List<M> getTableData(DataTableRequest dataTableRequest, String... fieldNames);

    Long countTableDataRecords(DataTableRequest dataTableRequest, String... fieldNames);

    Long countAllRecords();
}
