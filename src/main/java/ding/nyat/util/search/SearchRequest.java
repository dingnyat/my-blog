package ding.nyat.util.search;

import lombok.Data;

import java.util.List;

@Data
public class SearchRequest {
    private int draw;
    private int length;
    private int start;
    private List<SearchCriterion> searchCriteria;
}
