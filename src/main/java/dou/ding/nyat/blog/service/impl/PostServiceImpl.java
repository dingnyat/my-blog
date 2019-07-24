package dou.ding.nyat.blog.service.impl;

import dou.ding.nyat.blog.entity.AccountEntity;
import dou.ding.nyat.blog.entity.PostEntity;
import dou.ding.nyat.blog.model.Post;
import dou.ding.nyat.blog.model.Tag;
import dou.ding.nyat.blog.repository.AccountRepository;
import dou.ding.nyat.blog.repository.PostRepository;
import dou.ding.nyat.blog.repository.SeriesRepository;
import dou.ding.nyat.blog.repository.TagRepository;
import dou.ding.nyat.blog.service.PostService;
import dou.ding.nyat.blog.service.ServiceAbstract;
import dou.ding.nyat.blog.util.DateTimeUtils;
import dou.ding.nyat.blog.util.datatable.DataTableRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostServiceImpl extends ServiceAbstract<Integer, Post, PostEntity, PostRepository> implements PostService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    public PostServiceImpl(PostRepository repository) {
        super(repository);
    }

    @Override
    public Integer create(Post model) {
        PostEntity postEntity = new PostEntity();
        postEntity.setCode(model.getCode());
        postEntity.setTitle(model.getTitle());
        postEntity.setTags(model.getTags().stream().map(tag -> tagRepository.getById(tag.getId())).collect(Collectors.toSet()));
        if (model.getSeriesCode() != null && !model.getSeriesCode().isEmpty())
            postEntity.setSeries(seriesRepository.getByCode(model.getSeriesCode()));
        if (model.getPositionInSeries() != null) postEntity.setPositionInSeries(model.getPositionInSeries());
        postEntity.setContent(model.getContent());
        postEntity.setActived(true);
        return repository.create(postEntity);
    }

    @Override
    public void update(Post model) {
        PostEntity entity = repository.getById(model.getId());
        entity.setCode(model.getCode());
        entity.setTitle(model.getTitle());
        entity.setTags(model.getTags().stream().map(tag -> tagRepository.getById(tag.getId())).collect(Collectors.toSet()));
        if (model.getSeriesCode() != null && !model.getSeriesCode().isEmpty())
            entity.setSeries(seriesRepository.getByCode(model.getSeriesCode()));
        else entity.setSeries(null);
        if (model.getPositionInSeries() != null)
            entity.setPositionInSeries(model.getPositionInSeries());
        else entity.setPositionInSeries(null);
        entity.setContent(model.getContent());
        repository.update(entity);
    }

    @Override
    public Post convertToModel(PostEntity entity) {
        Post post = new Post();
        post.setId(entity.getId());
        post.setCode(entity.getCode());
        post.setTitle(entity.getTitle());
        post.setTags(entity.getTags().stream().map(tagEntity -> {
            Tag tag = new Tag();
            tag.setId(tagEntity.getId());
            tag.setCode(tagEntity.getCode());
            tag.setName(tagEntity.getName());
            return tag;
        }).collect(Collectors.toSet()));
        if (entity.getSeries() != null) {
            post.setSeriesCode(entity.getSeries().getCode());
            post.setSeriesName(entity.getSeries().getName());
        }
        post.setPositionInSeries(entity.getPositionInSeries());
        post.setContent(entity.getContent());
        post.setAuthorCode(entity.getAuthor().getCode());
        post.setAuthorName(entity.getAuthor().getName());
        post.setActived(entity.isActived());
        post.setCommentBlocked(entity.isCommentBlocked());
        post.setCreatedDate(DateTimeUtils.formatDate(entity.getCreatedDate(), DateTimeUtils.DD_MM_YYYY));
        post.setLastModifiedDate(DateTimeUtils.formatDate(entity.getLastModifiedDate(), DateTimeUtils.DD_MM_YYYY));
        post.setComments(new HashSet<>());
        mapper.map(entity.getComments(), post.getComments());
        return post;
    }

    @Override
    public List<Post> getTableData(String username, DataTableRequest dataTableRequest, String... fieldNames) {
        return repository.getTableData(username, dataTableRequest, fieldNames)
                .stream().map(this::convertToModel).collect(Collectors.toList());
    }

    @Override
    public Long countTableDataRecords(String username, DataTableRequest dataTableRequest, String... fieldNames) {
        return repository.countTableDataRecords(username, dataTableRequest, fieldNames);
    }

    @Override
    public Long countAllRecords(String username) {
        return repository.countAllRecords(username);
    }

    @Override
    public Boolean isAuthor(String username, Integer postId) {
        AccountEntity account = accountRepository.getByUsername(username);
        PostEntity post = repository.getById(postId);
        return post.getAuthor().getId() == account.getAuthor().getId();
    }
}
