package ding.nyat.util.datatable;

import java.util.List;

public class DataTableRequest {
    private int draw;
    private List<Column> columns;
    private int length;
    private int start;
    private Search search;
    private List<Order> order;

    public DataTableRequest() {
    }

    public String sortBy(Order order) {
        return this.columns.get(order.getColumn()).getData();
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }
}
