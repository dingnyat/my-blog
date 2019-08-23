package ding.nyat.repository;

import ding.nyat.util.datatable.DataTableRequest;
import ding.nyat.util.search.SearchCriteria;

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
