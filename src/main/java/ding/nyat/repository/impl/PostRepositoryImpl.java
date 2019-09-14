package ding.nyat.repository.impl;

import ding.nyat.entity.PostEntity;
import ding.nyat.repository.PostRepository;
import ding.nyat.repository.RepositoryAbstraction;
import ding.nyat.util.datatable.DataTableRequest;
import ding.nyat.util.search.SearchCriterion;
import ding.nyat.util.search.SearchRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class PostRepositoryImpl extends RepositoryAbstraction<PostEntity> implements PostRepository {
    @Override
    public PostEntity getByCode(String code) {
        try {
            return entityManager
                    .createQuery("SELECT p FROM PostEntity p WHERE p.code='" + code + "'", PostEntity.class)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }


    @Override
    public List<PostEntity> search(SearchRequest searchRequest) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PostEntity> criteriaQuery = criteriaBuilder.createQuery(PostEntity.class);
        Root<PostEntity> root = criteriaQuery.from(PostEntity.class);

        List<Predicate> predicates = new ArrayList<>();
        if (searchRequest.getSearchCriteria() != null) {
            for (SearchCriterion searchCriterion : searchRequest.getSearchCriteria()) {
                if (searchCriterion.getKey().equals("authorCode")) {
                    predicates.add(criteriaBuilder.equal(root.get("author").get("code"), searchCriterion.getValue()));
                }
            }
        }
        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[]{})));

        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createdDate")));

        TypedQuery<PostEntity> typedQuery = entityManager.createQuery(criteriaQuery.select(root));
        typedQuery.setFirstResult(searchRequest.getStart());
        typedQuery.setMaxResults(searchRequest.getLength());
        return typedQuery.getResultList();
    }

    @Override
    public int countSearchRecords(SearchRequest searchRequest) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> criteriaQuery = criteriaBuilder.createQuery(Integer.class);
        Root<PostEntity> root = criteriaQuery.from(PostEntity.class);

        List<Predicate> predicates = new ArrayList<>();
        if (searchRequest.getSearchCriteria() != null) {
            for (SearchCriterion searchCriterion : searchRequest.getSearchCriteria()) {
                if (searchCriterion.getKey().equals("authorCode")) {
                    predicates.add(criteriaBuilder.equal(root.get("author").get("code"), searchCriterion.getValue()));
                }
            }
        }
        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[]{})));

        return entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root).as(Integer.class))).getSingleResult();
    }

    @Override
    public List<PostEntity> getTableData(DataTableRequest dataTableRequest) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PostEntity> criteriaQuery = criteriaBuilder.createQuery(PostEntity.class);
        Root<PostEntity> root = criteriaQuery.from(PostEntity.class);

        List<Predicate> predicates = new ArrayList<>();
        if (dataTableRequest.getSearch().getValue() != null && !dataTableRequest.getSearch().getValue().isEmpty()) {
            for (String field : dataTableRequest.getSearchableFields()) {
                predicates.add(criteriaBuilder.like(root.get(field).as(String.class), "%" + dataTableRequest.getSearch().getValue() + "%"));
            }
            criteriaQuery.where(criteriaBuilder.or(predicates.toArray(new Predicate[]{})));
        }

        if (dataTableRequest.getSearchCriteria() != null) {
            List<Predicate> predicates2 = new ArrayList<>();
            for (SearchCriterion searchCriterion : dataTableRequest.getSearchCriteria()) {
                if (searchCriterion.getKey().equals("authorCode")) {
                    predicates2.add(criteriaBuilder.equal(root.get("author").get("code"), searchCriterion.getValue()));
                }
            }
            criteriaQuery.where(criteriaBuilder.and(predicates2.toArray(new Predicate[]{})));
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
        TypedQuery<PostEntity> typedQuery = entityManager.createQuery(criteriaQuery.select(root));
        typedQuery.setFirstResult(dataTableRequest.getStart());
        typedQuery.setMaxResults(dataTableRequest.getLength());
        return typedQuery.getResultList();
    }

    @Override
    public int countFilteredTableData(DataTableRequest dataTableRequest) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> criteriaQuery = criteriaBuilder.createQuery(Integer.class);
        Root<PostEntity> root = criteriaQuery.from(PostEntity.class);

        List<Predicate> predicates = new ArrayList<>();
        if (dataTableRequest.getSearch().getValue() != null && !dataTableRequest.getSearch().getValue().isEmpty()) {
            for (String field : dataTableRequest.getSearchableFields()) {
                predicates.add(criteriaBuilder.like(root.get(field).as(String.class), "%" + dataTableRequest.getSearch().getValue() + "%"));
            }
            criteriaQuery.where(criteriaBuilder.or(predicates.toArray(new Predicate[]{})));
        }

        if (dataTableRequest.getSearchCriteria() != null) {
            List<Predicate> predicates2 = new ArrayList<>();
            for (SearchCriterion searchCriterion : dataTableRequest.getSearchCriteria()) {
                if (searchCriterion.getKey().equals("authorCode")) {
                    predicates2.add(criteriaBuilder.equal(root.get("author").get("code"), searchCriterion.getValue()));
                }
            }
            criteriaQuery.where(criteriaBuilder.and(predicates2.toArray(new Predicate[]{})));
        }

        return entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root).as(Integer.class))).getSingleResult();
    }
}
