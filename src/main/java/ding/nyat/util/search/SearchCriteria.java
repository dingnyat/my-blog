package ding.nyat.util.search;

public class SearchCriteria {
    private String key;
    private SearchOperator operator;
    private Object value;

    public SearchCriteria() {
    }

    public SearchCriteria(String key, SearchOperator operator, Object value) {
        this.key = key;
        this.operator = operator;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public SearchOperator getOperator() {
        return operator;
    }

    public void setOperator(SearchOperator operator) {
        this.operator = operator;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
