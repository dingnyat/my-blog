package ding.nyat.util.datatable;

import ding.nyat.util.search.SearchRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DataTableRequest extends SearchRequest {
    private List<Column> columns;
    private Search search;
    private List<Order> order;

    public String sortBy(Order order) {
        return this.columns.get(order.getColumn()).getData();
    }

    public List<String> getSearchableFields() {
        List<String> searchableFields = new ArrayList<>();
        for (Column column : columns) {
            if (column.isSearchable()) searchableFields.add(column.getName());
        }
        return searchableFields;
    }

    public List<String> getOrderableFields() {
        List<String> orderableFields = new ArrayList<>();
        for (Column column : columns) {
            if (column.isOrderable()) orderableFields.add(column.getName());
        }
        return orderableFields;
    }
}
