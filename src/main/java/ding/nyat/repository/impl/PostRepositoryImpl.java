package ding.nyat.repository.impl;

import ding.nyat.entity.PostEntity;
import ding.nyat.repository.PostRepository;
import ding.nyat.repository.RepositoryAbstraction;
import ding.nyat.util.search.SearchCriteriaConsumer;
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
}
