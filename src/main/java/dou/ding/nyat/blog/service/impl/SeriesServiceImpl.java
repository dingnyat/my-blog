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
}
