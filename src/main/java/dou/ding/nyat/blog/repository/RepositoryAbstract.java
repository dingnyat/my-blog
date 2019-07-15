package dou.ding.nyat.blog.repository;

import dou.ding.nyat.blog.util.datatable.DataTableRequest;
import dou.ding.nyat.blog.util.search.SearchCriteria;
import dou.ding.nyat.blog.util.search.SearchCriteriaConsumer;
import dou.ding.nyat.blog.util.search.SearchOperator;
import org.hibernate.Session;
import org.springframework.lang.Nullable;

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

public abstract class RepositoryAbstract<PrimaryKeyType extends Serializable, E> {

    private final Class<E> entityClazz;

    private final String entityName;

    @PersistenceContext
    protected EntityManager entityManager;

    protected RepositoryAbstract() {
        this.entityClazz = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        final int index = this.entityClazz.getName().lastIndexOf('.');
        entityName = this.entityClazz.getName().substring(index + 1);
    }

    public PrimaryKeyType create(E entity) {
        entityManager.persist(entity);
        return (PrimaryKeyType) ((Session) entityManager.getDelegate()).getIdentifier(entity);
    }

    public void update(E entity) {
        entityManager.merge(entity);
    }

    public void delete(E entity) {
        entityManager.remove(entity);
    }

    public E getById(PrimaryKeyType id) {
        return entityManager.find(entityClazz, id);
    }

    public List<E> getAllRecords() {
        entityClazz.getName();
        return entityManager.createQuery("SELECT e FROM " + entityName + " e", entityClazz).getResultList();
    }

    public List<E> search(List<SearchCriteria> searchCriteria) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> criteriaQuery = criteriaBuilder.createQuery(entityClazz);
        Root<E> root = criteriaQuery.from(entityClazz);
        Predicate predicate = criteriaBuilder.conjunction();
        SearchCriteriaConsumer searchCriteriaConsumer = new SearchCriteriaConsumer(predicate, criteriaBuilder, root);
        searchCriteria.forEach(searchCriteriaConsumer);
        predicate = searchCriteriaConsumer.getPredicate();
        criteriaQuery.where(predicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public Long getTheNumberOfSearchedRecords(List<SearchCriteria> searchCriteria) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<E> root = criteriaQuery.from(entityClazz);
        Predicate predicate = criteriaBuilder.conjunction();
        SearchCriteriaConsumer searchCriteriaConsumer = new SearchCriteriaConsumer(predicate, criteriaBuilder, root);
        searchCriteria.forEach(searchCriteriaConsumer);
        predicate = searchCriteriaConsumer.getPredicate();
        criteriaQuery.where(predicate);
        return entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root))).getSingleResult();
    }

    public List<E> getTableData(DataTableRequest dataTableRequest, @Nullable String... fieldNames) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> criteriaQuery = criteriaBuilder.createQuery(entityClazz);
        Root<E> root = criteriaQuery.from(entityClazz);
        List<Predicate> predicates = new ArrayList<>();
        if (fieldNames != null && fieldNames.length > 0) {
            if (dataTableRequest.getSearch().getValue() != null && !dataTableRequest.getSearch().getValue().isEmpty()) {
                SearchCriteriaConsumer searchCriteriaConsumer = new SearchCriteriaConsumer(criteriaBuilder, root);
                for (String field : fieldNames) {
                    SearchCriteria searchCriteria = new SearchCriteria(field, SearchOperator.CONTAINS, dataTableRequest.getSearch().getValue());
                    predicates.add(searchCriteriaConsumer.createPredicate(searchCriteria));
                }
                criteriaQuery.where(criteriaBuilder.or(predicates.toArray(new Predicate[]{})));
            }
            String orderColumn = dataTableRequest.sortBy(dataTableRequest.getOrder().get(0));
            for (String field : fieldNames) {
                if (orderColumn.equalsIgnoreCase(field)) {
                    if (dataTableRequest.getOrder().get(0).getDir().equalsIgnoreCase("asc")) {
                        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(field)));
                    } else {
                        criteriaQuery.orderBy(criteriaBuilder.desc(root.get(field)));
                    }
                }
            }
        }
        TypedQuery<E> typedQuery = entityManager.createQuery(criteriaQuery.select(root));
        typedQuery.setFirstResult(dataTableRequest.getStart());
        typedQuery.setMaxResults(dataTableRequest.getLength());
        return typedQuery.getResultList();
    }

    public Long getTheNumberOfFilteredRecords(DataTableRequest dataTableRequest, @Nullable String... fieldNames) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<E> root = criteriaQuery.from(entityClazz);
        List<Predicate> predicates = new ArrayList<>();
        if (dataTableRequest.getSearch().getValue() != null && !dataTableRequest.getSearch().getValue().isEmpty() && fieldNames != null && fieldNames.length > 0) {
            SearchCriteriaConsumer searchCriteriaConsumer = new SearchCriteriaConsumer(criteriaBuilder, root);
            for (String field : fieldNames) {
                SearchCriteria searchCriteria = new SearchCriteria(field, SearchOperator.CONTAINS, dataTableRequest.getSearch().getValue());
                predicates.add(searchCriteriaConsumer.createPredicate(searchCriteria));
            }
            criteriaQuery.where(criteriaBuilder.or(predicates.toArray(new Predicate[]{})));
        }
        return entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root))).getSingleResult();
    }

    public Long getTheNumberOfAllRecords() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<E> root = criteriaQuery.from(entityClazz);
        criteriaQuery.where((new ArrayList<Predicate>()).toArray(new Predicate[]{}));
        return entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root))).getSingleResult();
    }
}
