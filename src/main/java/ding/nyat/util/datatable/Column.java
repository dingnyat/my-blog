package ding.nyat.util.datatable;

import lombok.Data;

@Data
public class Column {
    private String data;
    private String name;
    private boolean orderable;
    private boolean searchable;
    private Search search;
}
