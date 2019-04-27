package dou.ding.nyat.blog.util.search;

public enum SearchOperator {
    EQUALITY(":"), NEGATION("!"), GREATER_THAN(">"), LESS_THAN("<"),
    LIKE("~"), STARTS_WITH(null), ENDS_WITH(null), CONTAINS(null);

    // not have start, end, contain operator
    public static final String[] SIMPLE_OPERATION_SET = {":", "!", ">", "<", "~"};

    private String sign;

    SearchOperator(String sign) {
        this.sign = sign;
    }

    public String getSign() {
        return sign;
    }

    public static SearchOperator getOperator(String sign) {
        if (sign != null && !sign.isEmpty()) {
            for (SearchOperator searchOperator : SearchOperator.values()) {
                if (sign.equalsIgnoreCase(searchOperator.getSign())) {
                    return searchOperator;
                }
            }
        }
        return null;
    }
}
