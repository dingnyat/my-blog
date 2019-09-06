package ding.nyat.service;

import ding.nyat.annotation.Identifier;
import ding.nyat.repository.RepositoryInterface;
import ding.nyat.util.datatable.DataTableRequest;
import ding.nyat.util.search.SearchRequest;
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
        mapper.map(model, entity);
        repository.update(entity);
    }

    public void delete(Serializable id) {
        E entity = repository.read(id);
        repository.delete(entity);
    }

    public M read(Serializable id) {
        E entity = repository.read(id);
        return convertToModel(entity);
    }

    public List<M> readAll() {
        List<E> entities = repository.readAll();
        return entities.stream().map(this::convertToModel).collect(Collectors.toList());
    }

    public <S extends SearchRequest> List<M> search(S searchRequest) {
        List<E> entities = repository.search(searchRequest);
        return entities.stream().map(this::convertToModel).collect(Collectors.toList());
    }

    public <S extends SearchRequest> int countSearchRecords(S searchRequest) {
        return repository.countSearchRecords(searchRequest);
    }

    public List<M> getTableData(DataTableRequest dataTableRequest) {
        List<E> entities = repository.getTableData(dataTableRequest);
        return entities.stream().map(this::convertToModel).collect(Collectors.toList());
    }

    public int countTableDataRecords(DataTableRequest dataTableRequest) {
        return repository.countFilteredTableData(dataTableRequest);
    }

    public int countAllRecords() {
        return repository.countAllRecords();
    }

    public M convertToModel(E entity) {
        return mapper.map(entity, modelClazz);
    }
}
