package ding.nyat.service.impl;

import ding.nyat.repository.SeriesRepository;
import ding.nyat.entity.SeriesEntity;
import ding.nyat.model.Series;
import ding.nyat.service.SeriesService;
import ding.nyat.service.ServiceAbstract;
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
