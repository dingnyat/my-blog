package ding.nyat.service;

import ding.nyat.model.Series;

public interface SeriesService extends ServiceInterface<Series> {
    Series getByCode(String code);
}
