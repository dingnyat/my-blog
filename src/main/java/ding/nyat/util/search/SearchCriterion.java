package ding.nyat.util.search;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchCriterion {
    private String key;
    private SearchOperator operator;
    private Object value;
}
