package ding.nyat.service;

import ding.nyat.util.datatable.DataTableRequest;
import ding.nyat.util.search.SearchCriteria;
import ding.nyat.model.CommonModel;
import ding.nyat.repository.RepositoryInterface;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
public abstract class ServiceAbstract<PrimaryKeyType extends Serializable, M extends CommonModel, E, D extends RepositoryInterface> {

    protected D repository;

    protected ModelMapper mapper;

    private Class<M> modelClazz;
    private Class<E> entityClazz;

    public ServiceAbstract(D repository) {
        this.repository = repository;
        this.mapper = new ModelMapper();
        this.modelClazz = (Class<M>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        this.entityClazz = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[2];
    }

    public PrimaryKeyType create(M model) {
        return (PrimaryKeyType) repository.create(mapper.map(model, entityClazz));
    }

    public void update(M model) {
        E entity = (E) repository.getById(model.getId());
        mapper.map(model, entity);
        repository.update(entity);
    }

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

    public M convertToModel(E entity) {
        return mapper.map(entity, modelClazz);
    }
}
