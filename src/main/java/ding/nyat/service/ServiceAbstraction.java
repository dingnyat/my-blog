package ding.nyat.service;

import ding.nyat.annotation.Identifier;
import ding.nyat.repository.RepositoryInterface;
import ding.nyat.util.datatable.DataTableRequest;
import ding.nyat.util.datatable.DataTableResponse;
import ding.nyat.util.search.SearchRequest;
import ding.nyat.util.search.SearchResponse;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
public abstract class ServiceAbstraction<M, E, R extends RepositoryInterface<E>> {

    protected R repository;
    protected ModelMapper mapper;
    private Class<M> modelClazz;
    private Class<E> entityClazz;

    protected ServiceAbstraction(R repository) {
        this.repository = repository;
        this.mapper = new ModelMapper();
        this.modelClazz = (Class<M>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.entityClazz = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    public void create(M model) {
        repository.create(mapper.map(model, entityClazz));
    }

    public void update(M model) throws IllegalAccessException {
        Serializable id = null;
        for (Field field : model.getClass().getDeclaredFields()) {
            Annotation annotation = field.getAnnotation(Identifier.class);
            if (annotation != null) {
                field.setAccessible(true);
                id = (Serializable) field.get(model);
                field.setAccessible(false);
            }
        }
        E entity = repository.read(id);
        if (entity != null) {
            mapper.map(model, entity);
            repository.update(entity);
        }
    }

    public void delete(Serializable id) {
        E entity = repository.read(id);
        if (entity != null) repository.delete(entity);
    }

    public M read(Serializable id) {
        E entity = repository.read(id);
        return entity != null ? convertToModel(entity) : null;
    }

    public List<M> readAll() {
        List<E> entities = repository.readAll();
        return entities.stream().map(this::convertToModel).collect(Collectors.toList());
    }

    public SearchResponse<M> search(SearchRequest searchRequest) {
        SearchResponse<M> response = new SearchResponse<>();
        List<E> entities = repository.search(searchRequest);
        response.setData(entities.stream().map(this::convertToModel).collect(Collectors.toList()));
        response.setDraw(searchRequest.getDraw());
        response.setRecordsFiltered(repository.countSearchRecords(searchRequest));
        response.setRecordsTotal(repository.countAllRecords());
        return response;
    }

    public DataTableResponse<M> getTableData(DataTableRequest dataTableRequest) {
        DataTableResponse<M> response = new DataTableResponse<>();
        List<E> entities = repository.getTableData(dataTableRequest);
        response.setData(entities.stream().map(this::convertToModel).collect(Collectors.toList()));
        response.setDraw(dataTableRequest.getDraw());
        response.setRecordsFiltered(repository.countFilteredTableData(dataTableRequest));
        response.setRecordsTotal(repository.countAllRecords());
        return response;
    }

    public int countAllRecords() {
        return repository.countAllRecords();
    }

    public M convertToModel(E entity) {
        return mapper.map(entity, modelClazz);
    }
}
