package ding.nyat.repository;

import ding.nyat.util.datatable.DataTableRequest;
import ding.nyat.util.search.SearchCriteriaConsumer;
import ding.nyat.util.search.SearchRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public abstract class RepositoryAbstraction<E> {

    private final Class<E> entityClazz;

    private final String entityClassName;

    @PersistenceContext
    protected EntityManager entityManager;

    protected RepositoryAbstraction() {
        this.entityClazz = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        final int index = this.entityClazz.getName().lastIndexOf('.');
        entityClassName = this.entityClazz.getName().substring(index + 1);
    }

    public void create(E entity) {
        entityManager.persist(entity);
    }

    public void update(E entity) {
        entityManager.merge(entity);
    }

    public void delete(E entity) {
        entityManager.remove(entity);
    }

    public E read(Serializable id) {
        return entityManager.find(entityClazz, id);
    }

    public List<E> readAll() {
        return entityManager.createQuery("SELECT e FROM " + entityClassName + " e", entityClazz).getResultList();
    }

    public List<E> search(SearchRequest searchRequest) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> criteriaQuery = criteriaBuilder.createQuery(entityClazz);
        Root<E> root = criteriaQuery.from(entityClazz);

        //TODO nên có cơ chế ngăn chặn ko search được field cấm (dùng annotation đánh dấu)
        Predicate predicate = criteriaBuilder.conjunction();
        if (searchRequest.getSearchCriteria() != null) {
            SearchCriteriaConsumer searchCriteriaConsumer = new SearchCriteriaConsumer(predicate, criteriaBuilder, root);
            searchRequest.getSearchCriteria().forEach(searchCriteriaConsumer);
            predicate = searchCriteriaConsumer.getPredicate();
        }
        criteriaQuery.where(predicate);

        TypedQuery<E> typedQuery = entityManager.createQuery(criteriaQuery.select(root));
        typedQuery.setFirstResult(searchRequest.getStart());
        typedQuery.setMaxResults(searchRequest.getLength());
        return typedQuery.getResultList();
    }

    public int countSearchRecords(SearchRequest searchRequest) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> criteriaQuery = criteriaBuilder.createQuery(Integer.class);
        Root<E> root = criteriaQuery.from(entityClazz);

        //TODO nên có cơ chế ngăn chặn ko search được field cấm (dùng annotation đánh dấu)
        Predicate predicate = criteriaBuilder.conjunction();
        if (searchRequest.getSearchCriteria() != null) {
            SearchCriteriaConsumer searchCriteriaConsumer = new SearchCriteriaConsumer(predicate, criteriaBuilder, root);
            searchRequest.getSearchCriteria().forEach(searchCriteriaConsumer);
            predicate = searchCriteriaConsumer.getPredicate();
        }
        criteriaQuery.where(predicate);

        return entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root).as(Integer.class))).getSingleResult();
    }

    public List<E> getTableData(DataTableRequest dataTableRequest) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> criteriaQuery = criteriaBuilder.createQuery(entityClazz);
        Root<E> root = criteriaQuery.from(entityClazz);

        /*TODO check lại logic xem người dùng có thể lợi dụng field để truy cập field trái phép ko?*/
        // nên có cơ chế ngăn chặn ko search được field cấm (dùng annotation đánh dấu)
        List<Predicate> predicates = new ArrayList<>();
        if (dataTableRequest.getSearch().getValue() != null && !dataTableRequest.getSearch().getValue().isEmpty()) {
            for (String field : dataTableRequest.getSearchableFields()) {
                predicates.add(criteriaBuilder.like(root.get(field).as(String.class), "%" + dataTableRequest.getSearch().getValue() + "%"));
            }
            criteriaQuery.where(criteriaBuilder.or(predicates.toArray(new Predicate[]{})));
        }

        String orderColumn = dataTableRequest.sortBy(dataTableRequest.getOrder().get(0));
        for (String field : dataTableRequest.getOrderableFields()) {
            if (orderColumn.equalsIgnoreCase(field)) {
                if (dataTableRequest.getOrder().get(0).getDir().equalsIgnoreCase("asc")) {
                    criteriaQuery.orderBy(criteriaBuilder.asc(root.get(field)));
                } else {
                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get(field)));
                }
            }
        }
        TypedQuery<E> typedQuery = entityManager.createQuery(criteriaQuery.select(root));
        typedQuery.setFirstResult(dataTableRequest.getStart());
        typedQuery.setMaxResults(dataTableRequest.getLength());
        return typedQuery.getResultList();
    }

    public int countFilteredTableData(DataTableRequest dataTableRequest) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> criteriaQuery = criteriaBuilder.createQuery(Integer.class);
        Root<E> root = criteriaQuery.from(entityClazz);

        /*TODO check lại logic xem người dùng có thể lợi dụng field để truy cập field trái phép ko?*/
        // nên có cơ chế ngăn chặn ko search được field cấm (dùng annotation đánh dấu)
        List<Predicate> predicates = new ArrayList<>();
        if (dataTableRequest.getSearch().getValue() != null && !dataTableRequest.getSearch().getValue().isEmpty()) {
            for (String field : dataTableRequest.getSearchableFields()) {
                predicates.add(criteriaBuilder.like(root.get(field).as(String.class), "%" + dataTableRequest.getSearch().getValue() + "%"));
            }
            criteriaQuery.where(criteriaBuilder.or(predicates.toArray(new Predicate[]{})));
        }

        return entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root).as(Integer.class))).getSingleResult();
    }

    public int countAllRecords() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> criteriaQuery = criteriaBuilder.createQuery(Integer.class);
        Root<E> root = criteriaQuery.from(entityClazz);
        criteriaQuery.where((new ArrayList<Predicate>()).toArray(new Predicate[]{}));
        return entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root).as(Integer.class))).getSingleResult();
    }
}
