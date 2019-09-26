package ding.nyat.util.search;

import lombok.Data;

import java.util.List;

@Data
public class SearchResponse<T> {
    private int draw;
    private int totalDraw;
    private int recordsTotal;
    private int recordsFiltered;
    private List<T> data;
}
