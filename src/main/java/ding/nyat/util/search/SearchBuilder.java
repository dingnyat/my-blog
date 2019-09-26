package ding.nyat.util.search;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchBuilder {

    private List<SearchCriterion> searchCriteria;

    private static Pattern pattern;

    public SearchBuilder() {
        this.searchCriteria = new ArrayList<>();
        final String searchRegex = "(\\w+?)(" + String.join("|", SearchOperator.SIMPLE_OPERATION_SET) + ")(\\p{Punct}?)(\\w+)(\\p{Punct}?)";
        pattern = Pattern.compile(searchRegex + ",");
    }

    public SearchBuilder with(String key, String operator, String prefix, Object value, String suffix) {
        SearchOperator searchOperator = SearchOperator.getOperator(operator);
        if (searchOperator != null) {
            if (searchOperator == SearchOperator.EQUALITY) {
                boolean startWithAsterisk = prefix.contains("*");
                boolean endWithAsterisk = suffix.contains("*");
                if (startWithAsterisk && endWithAsterisk) {
                    searchOperator = SearchOperator.CONTAINS;
                } else if (startWithAsterisk) {
                    searchOperator = SearchOperator.ENDS_WITH;
                } else if (endWithAsterisk) {
                    searchOperator = SearchOperator.STARTS_WITH;
                }
            }
            this.searchCriteria.add(new SearchCriterion(key, searchOperator, value));
        }
        return this;
    }

    public List<SearchCriterion> build() {
        return this.searchCriteria;
    }

    public static SearchBuilder parseSearchExp(String searchExp) {
        SearchBuilder searchBuilder = new SearchBuilder();
        Matcher matcher = pattern.matcher(searchExp + ",");
        while (matcher.find()) {
            searchBuilder.with(matcher.group(1),
                    matcher.group(2),
                    matcher.group(3),
                    matcher.group(4),
                    matcher.group(5));
        }
        return searchBuilder;
    }
}
