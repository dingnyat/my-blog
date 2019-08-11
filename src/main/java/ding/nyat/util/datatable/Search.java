package ding.nyat.util.datatable;

public class Search {
    private boolean regex;
    private String value;

    public Search() {
    }

    public boolean isRegex() {
        return regex;
    }

    public void setRegex(boolean regex) {
        this.regex = regex;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
