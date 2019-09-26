package ding.nyat.service.impl;

import ding.nyat.entity.SeriesEntity;
import ding.nyat.model.Series;
import ding.nyat.repository.SeriesRepository;
import ding.nyat.service.SeriesService;
import ding.nyat.service.ServiceAbstraction;
import ding.nyat.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class SeriesServiceImpl extends ServiceAbstraction<Series, SeriesEntity, SeriesRepository> implements SeriesService {

    private SeriesRepository repository;

    @Autowired
    public SeriesServiceImpl(SeriesRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Series getByCode(String code) {
        SeriesEntity seriesEntity = repository.getByCode(code);
        if (seriesEntity != null) return convertToModel(seriesEntity);
        return null;
    }

    @Override
    public Series convertToModel(SeriesEntity entity) {
        Series series = new Series();
        series.setId(entity.getId());
        series.setCode(entity.getCode());
        series.setName(entity.getName());
        series.setDescription(entity.getDescription());
        Set<Pair<String, String>> posts = new HashSet<>();
        entity.getPosts().forEach(postEntity -> {
            posts.add(new Pair<>(postEntity.getCode(), postEntity.getTitle()));
        });
        series.setPosts(posts);
        return series;
    }
}
