package dou.ding.nyat.blog.service;

import dou.ding.nyat.blog.util.datatable.DataTableRequest;
import dou.ding.nyat.blog.util.search.SearchCriteria;

import java.io.Serializable;
import java.util.List;

public interface ServiceInterface<PrimaryKeyType extends Serializable, M> {
    PrimaryKeyType create(M model);

    void update(M model);

    void delete(PrimaryKeyType id);

    M getById(PrimaryKeyType id);

    List<M> getAllRecords();

    List<M> search(List<SearchCriteria> searchCriteria);

    Long getTheNumberOfSearchedRecords(List<SearchCriteria> searchCriteria);

    List<M> getTableData(DataTableRequest dataTableRequest, String... fieldNames);

    Long getTheNumberOfFilteredRecords(DataTableRequest dataTableRequest, String... fieldNames);

    Long getTheNumberOfAllRecords();
}
