package dou.ding.nyat.blog.repository;

import dou.ding.nyat.blog.util.datatable.DataTableRequest;
import dou.ding.nyat.blog.util.search.SearchCriteria;

import java.io.Serializable;
import java.util.List;

public interface RepositoryInterface<PrimaryKeyType extends Serializable, E> {
    PrimaryKeyType create(E entity);

    void update(E entity);

    void delete(E entity);

    E getById(PrimaryKeyType id);

    List<E> getAllRecords();

    List<E> search(List<SearchCriteria> searchCriteria);

    Long getTheNumberOfSearchedRecords(List<SearchCriteria> searchCriteria);

    List<E> getTableData(DataTableRequest dataTableRequest, String... fieldNames);

    Long getTheNumberOfFilteredRecords(DataTableRequest dataTableRequest, String... fieldNames);

    Long getTheNumberOfAllRecords();
}