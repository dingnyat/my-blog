package ding.nyat.util.search;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import java.util.function.Consumer;

public class SearchCriteriaConsumer implements Consumer<SearchCriteria> {

    protected Predicate predicate;
    protected CriteriaBuilder criteriaBuilder;
    protected From root;

    public SearchCriteriaConsumer() {
    }

    public SearchCriteriaConsumer(CriteriaBuilder criteriaBuilder, From root) {
        this.criteriaBuilder = criteriaBuilder;
        this.root = root;
    }

    public SearchCriteriaConsumer(Predicate predicate, CriteriaBuilder criteriaBuilder, From root) {
        this.predicate = predicate;
        this.criteriaBuilder = criteriaBuilder;
        this.root = root;
    }

    public Predicate getPredicate() {
        return predicate;
    }

    public void setPredicate(Predicate predicate) {
        this.predicate = predicate;
    }

    public CriteriaBuilder getCriteriaBuilder() {
        return criteriaBuilder;
    }

    public void setCriteriaBuilder(CriteriaBuilder criteriaBuilder) {
        this.criteriaBuilder = criteriaBuilder;
    }

    public From getRoot() {
        return root;
    }

    public void setRoot(From root) {
        this.root = root;
    }

    public Predicate createPredicate(SearchCriteria searchCriteria) {
        switch (searchCriteria.getOperator()) {
            case EQUALITY:
                return criteriaBuilder.equal(root.get(searchCriteria.getKey()), searchCriteria.getValue());
            case NEGATION:
                return criteriaBuilder.notEqual(root.get(searchCriteria.getKey()), searchCriteria.getValue());
            case GREATER_THAN:
                return criteriaBuilder.greaterThanOrEqualTo(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
            case LESS_THAN:
                return criteriaBuilder.lessThanOrEqualTo(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
            case LIKE:
                return criteriaBuilder.like(root.get(searchCriteria.getKey()).as(String.class), searchCriteria.getValue().toString());
            case CONTAINS:
                return criteriaBuilder.like(root.get(searchCriteria.getKey()).as(String.class), "%" + searchCriteria.getValue() + "%");
            case STARTS_WITH:
                return criteriaBuilder.like(root.get(searchCriteria.getKey()).as(String.class), searchCriteria.getValue() + "%");
            case ENDS_WITH:
                return criteriaBuilder.like(root.get(searchCriteria.getKey()).as(String.class), "%" + searchCriteria.getValue());
            default:
                break;
        }
        return null;
    }

    @Override
    public void accept(SearchCriteria searchCriteria) {
        switch (searchCriteria.getOperator()) {
            case EQUALITY:
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get(searchCriteria.getKey()), searchCriteria.getValue()));
                break;
            case NEGATION:
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.notEqual(root.get(searchCriteria.getKey()), searchCriteria.getValue()));
                break;
            case GREATER_THAN:
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString()));
                break;
            case LESS_THAN:
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString()));
                break;
            case LIKE:
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get(searchCriteria.getKey()).as(String.class), searchCriteria.getValue().toString()));
                break;
            case CONTAINS:
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get(searchCriteria.getKey()).as(String.class), "%" + searchCriteria.getValue() + "%"));
                break;
            case STARTS_WITH:
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get(searchCriteria.getKey()).as(String.class), searchCriteria.getValue() + "%"));
                break;
            case ENDS_WITH:
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get(searchCriteria.getKey()).as(String.class), "%" + searchCriteria.getValue()));
                break;
            default:
                break;
        }
    }
}
