package ding.nyat.util.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriterion {
    private String key;
    private SearchOperator operator;
    private Object value;
}
