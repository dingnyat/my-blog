package dou.ding.nyat.blog.service;

import dou.ding.nyat.blog.model.CommonModel;
import dou.ding.nyat.blog.repository.RepositoryInterface;
import dou.ding.nyat.blog.util.BeanTools;
import dou.ding.nyat.blog.util.datatable.DataTableRequest;
import dou.ding.nyat.blog.util.search.SearchCriteria;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
public abstract class ServiceAbstract<PrimaryKeyType extends Serializable, M extends CommonModel, E, D extends RepositoryInterface> {

    protected D repository;
    protected BeanTools beanTools;
    private Class<E> entityClazz;
    private Class<M> modelClazz;

    public ServiceAbstract(D repository, BeanTools beanTools) {
        this.repository = repository;
        this.beanTools = beanTools;
        this.entityClazz = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[2];
        this.modelClazz = (Class<M>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    public PrimaryKeyType create(M model) {
        try {
            return (PrimaryKeyType) repository.create(beanTools.map(model, entityClazz.getConstructor().newInstance()));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void update(M model) {
        E entity = (E) repository.getById(model.getId());
        beanTools.map(model, entity);
        repository.update(entity);
    }

    public void delete(PrimaryKeyType id) {
        E entity = (E) repository.getById(id);
        repository.delete(entity);
    }

    public M getById(PrimaryKeyType id) {
        E entity = (E) repository.getById(id);
        try {
            return beanTools.map(entity, modelClazz.getConstructor().newInstance());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<M> getAllRecords() {
        List<E> entityList = repository.getAllRecords();
        return entityList.stream()
                .map(entity -> {
                    try {
                        return beanTools.map(entity, modelClazz.getConstructor().newInstance());
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        return null;
                    }
                })
                .collect(Collectors.toList());
    }

    public List<M> search(List<SearchCriteria> searchCriteria) {
        List<E> entityList = repository.search(searchCriteria);
        return entityList.stream()
                .map(entity -> {
                    try {
                        return beanTools.map(entity, modelClazz.getConstructor().newInstance());
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        return null;
                    }
                })
                .collect(Collectors.toList());
    }

    public Long getTheNumberOfSearchedRecords(List<SearchCriteria> searchCriteria) {
        return repository.getTheNumberOfSearchedRecords(searchCriteria);
    }

    public List<M> getTableData(DataTableRequest dataTableRequest, String... fieldNames) {
        List<E> entityList = repository.getTableData(dataTableRequest, fieldNames);
        return entityList.stream()
                .map(entity -> {
                    try {
                        return beanTools.map(entity, modelClazz.getConstructor().newInstance());
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        return null;
                    }
                })
                .collect(Collectors.toList());
    }

    public Long getTheNumberOfFilteredRecords(DataTableRequest dataTableRequest, String... fieldNames) {
        return repository.getTheNumberOfFilteredRecords(dataTableRequest, fieldNames);
    }

    public Long getTheNumberOfAllRecords() {
        return repository.getTheNumberOfAllRecords();
    }
}
