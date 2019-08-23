package ding.nyat.repository.impl;

import ding.nyat.entity.AuthorEntity;
import ding.nyat.entity.PostEntity;
import ding.nyat.repository.PostRepository;
import ding.nyat.repository.RepositoryAbstract;
import ding.nyat.util.datatable.DataTableRequest;
import ding.nyat.util.search.SearchCriteria;
import ding.nyat.util.search.SearchCriteriaConsumer;
import ding.nyat.util.search.SearchOperator;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class PostRepositoryImpl extends RepositoryAbstract<Integer, PostEntity> implements PostRepository {
    @Override
    public List<PostEntity> getTableData(String username, DataTableRequest dataTableRequest, String... fieldNames) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PostEntity> postQuery = builder.createQuery(PostEntity.class);
        Root<PostEntity> postRoot = postQuery.from(PostEntity.class);
        Join<AuthorEntity, PostEntity> postAuthorJoin = postRoot.join("author");


        List<Predicate> predicates = new ArrayList<>();

        if (fieldNames != null && fieldNames.length > 0) {
            if (dataTableRequest.getSearch().getValue() != null && !dataTableRequest.getSearch().getValue().isEmpty()) {
                SearchCriteriaConsumer searchCriteriaConsumer = new SearchCriteriaConsumer(builder, postRoot);
                for (String field : fieldNames) {
                    SearchCriteria searchCriteria = new SearchCriteria(field, SearchOperator.CONTAINS, dataTableRequest.getSearch().getValue());
                    predicates.add(searchCriteriaConsumer.createPredicate(searchCriteria));
                }
            }
            String orderColumn = dataTableRequest.sortBy(dataTableRequest.getOrder().get(0));
            for (String field : fieldNames) {
                if (orderColumn.equalsIgnoreCase(field)) {
                    if (dataTableRequest.getOrder().get(0).getDir().equalsIgnoreCase("asc")) {
                        postQuery.orderBy(builder.asc(postRoot.get(field)));
                    } else {
                        postQuery.orderBy(builder.desc(postRoot.get(field)));
                    }
                }
            }
        }

        predicates.add(builder.equal(postAuthorJoin.get("account").get("username"), username));

        postQuery.where(builder.or(predicates.toArray(new Predicate[]{})));

        TypedQuery<PostEntity> typedQuery = entityManager.createQuery(postQuery.select(postRoot));
        typedQuery.setFirstResult(dataTableRequest.getStart());
        typedQuery.setMaxResults(dataTableRequest.getLength());
        return typedQuery.getResultList();
    }

    @Override
    public Long countTableDataRecords(String username, DataTableRequest dataTableRequest, String... fieldNames) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> postQuery = builder.createQuery(Long.class);
        Root<PostEntity> postRoot = postQuery.from(PostEntity.class);
        Join<AuthorEntity, PostEntity> postAuthorJoin = postRoot.join("author");


        List<Predicate> predicates = new ArrayList<>();

        if (fieldNames != null && fieldNames.length > 0 && dataTableRequest.getSearch().getValue() != null && !dataTableRequest.getSearch().getValue().isEmpty()) {
            SearchCriteriaConsumer searchCriteriaConsumer = new SearchCriteriaConsumer(builder, postRoot);
            for (String field : fieldNames) {
                SearchCriteria searchCriteria = new SearchCriteria(field, SearchOperator.CONTAINS, dataTableRequest.getSearch().getValue());
                predicates.add(searchCriteriaConsumer.createPredicate(searchCriteria));
            }
        }

        predicates.add(builder.equal(postAuthorJoin.get("account").get("username"), username));

        postQuery.where(builder.or(predicates.toArray(new Predicate[]{})));

        return entityManager.createQuery(postQuery.select(builder.count(postRoot))).getSingleResult();
    }

    @Override
    public Long countAllRecords(String username) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> postQuery = builder.createQuery(Long.class);
        Root<PostEntity> postRoot = postQuery.from(PostEntity.class);
        Join<AuthorEntity, PostEntity> postAuthorJoin = postRoot.join("author");

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(builder.equal(postAuthorJoin.get("account").get("username"), username));

        postQuery.where(builder.or(predicates.toArray(new Predicate[]{})));

        return entityManager.createQuery(postQuery.select(builder.count(postRoot))).getSingleResult();
    }

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
}
