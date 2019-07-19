package dou.ding.nyat.blog.service.impl;

import dou.ding.nyat.blog.entity.SeriesEntity;
import dou.ding.nyat.blog.model.Series;
import dou.ding.nyat.blog.repository.SeriesRepository;
import dou.ding.nyat.blog.service.SeriesService;
import dou.ding.nyat.blog.service.ServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SeriesServiceImpl extends ServiceAbstract<Integer, Series, SeriesEntity, SeriesRepository> implements SeriesService {
    @Autowired
    public SeriesServiceImpl(SeriesRepository repository) {
        super(repository);
    }

    @Override
    public Integer create(Series model) {
        SeriesEntity seriesEntity = new SeriesEntity();
        seriesEntity.setCode(model.getCode());
        seriesEntity.setName(model.getName());
        seriesEntity.setDescription(model.getDescription());
        return repository.create(seriesEntity);
    }

    @Override
    public void update(Series model) {
        SeriesEntity seriesEntity = repository.getById(model.getId());
        seriesEntity.setCode(model.getCode());
        seriesEntity.setName(model.getName());
        seriesEntity.setDescription(model.getDescription());
        repository.update(seriesEntity);
    }

    @Override
    public Series convertToModel(SeriesEntity entity) {
        Series series = new Series();
        series.setId(entity.getId());
        series.setCode(entity.getCode());
        series.setName(entity.getName());
        series.setDescription(entity.getDescription());
        return series;
    }
}
