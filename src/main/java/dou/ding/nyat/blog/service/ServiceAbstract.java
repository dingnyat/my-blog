package dou.ding.nyat.blog.service;

import dou.ding.nyat.blog.model.CommonModel;
import dou.ding.nyat.blog.repository.RepositoryInterface;
import dou.ding.nyat.blog.util.datatable.DataTableRequest;
import dou.ding.nyat.blog.util.search.SearchCriteria;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
public abstract class ServiceAbstract<PrimaryKeyType extends Serializable, M extends CommonModel, E, D extends RepositoryInterface> {

    protected D repository;

    public ServiceAbstract(D repository) {
        this.repository = repository;
    }

    public abstract PrimaryKeyType create(M model);

    public abstract void update(M model);

    public void delete(PrimaryKeyType id) {
        E entity = (E) repository.getById(id);
        repository.delete(entity);
    }

    public M getById(PrimaryKeyType id) {
        E entity = (E) repository.getById(id);
        return convertToModel(entity);
    }

    public List<M> getAllRecords() {
        List<E> entityList = repository.getAllRecords();
        return entityList.stream().map(this::convertToModel).collect(Collectors.toList());
    }

    public List<M> search(List<SearchCriteria> searchCriteria) {
        List<E> entityList = repository.search(searchCriteria);
        return entityList.stream().map(this::convertToModel).collect(Collectors.toList());
    }

    public Long countSearchedRecords(List<SearchCriteria> searchCriteria) {
        return repository.getTheNumberOfSearchedRecords(searchCriteria);
    }

    public List<M> getTableData(DataTableRequest dataTableRequest, String... fieldNames) {
        List<E> entityList = repository.getTableData(dataTableRequest, fieldNames);
        return entityList.stream().map(this::convertToModel).collect(Collectors.toList());
    }

    public Long countTableDataRecords(DataTableRequest dataTableRequest, String... fieldNames) {
        return repository.getTheNumberOfFilteredRecords(dataTableRequest, fieldNames);
    }

    public Long countAllRecords() {
        return repository.getTheNumberOfAllRecords();
    }

    public abstract M convertToModel(E entity);
}
