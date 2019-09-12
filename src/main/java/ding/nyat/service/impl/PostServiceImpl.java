package ding.nyat.service.impl;

import ding.nyat.entity.AccountEntity;
import ding.nyat.entity.CommentEntity;
import ding.nyat.entity.PostEntity;
import ding.nyat.model.Comment;
import ding.nyat.model.Post;
import ding.nyat.model.Tag;
import ding.nyat.repository.*;
import ding.nyat.service.PostService;
import ding.nyat.service.ServiceAbstraction;
import ding.nyat.util.DateTimeUtils;
import ding.nyat.util.search.SearchRequest;
import ding.nyat.util.search.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostServiceImpl extends ServiceAbstraction<Post, PostEntity, PostRepository> implements PostService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CommentRepository commentRepository;

    private PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository repository) {
        super(repository);
        this.postRepository = repository;
    }

    @Override
    public void create(Post model) {
        PostEntity postEntity = new PostEntity();
        postEntity.setCode(model.getCode());
        postEntity.setTitle(model.getTitle());
        postEntity.setTags(model.getTags().stream().map(tag -> tagRepository.read(tag.getId())).collect(Collectors.toSet()));
        if (model.getSeriesCode() != null && !model.getSeriesCode().isEmpty())
            postEntity.setSeries(seriesRepository.getByCode(model.getSeriesCode()));
        if (model.getPositionInSeries() != null) postEntity.setPositionInSeries(model.getPositionInSeries());
        postEntity.setContent(model.getContent());
        postEntity.setActive(true);
        postEntity.setCommentBlocked(false);
        postRepository.create(postEntity);
    }

    @Override
    public void update(Post model) {
        PostEntity entity = postRepository.read(model.getId());
        entity.setCode(model.getCode());
        entity.setTitle(model.getTitle());
        entity.setTags(model.getTags().stream().map(tag -> tagRepository.read(tag.getId())).collect(Collectors.toSet()));
        if (model.getSeriesCode() != null && !model.getSeriesCode().isEmpty())
            entity.setSeries(seriesRepository.getByCode(model.getSeriesCode()));
        else entity.setSeries(null);
        if (model.getPositionInSeries() != null)
            entity.setPositionInSeries(model.getPositionInSeries());
        else entity.setPositionInSeries(null);
        entity.setContent(model.getContent());
        postRepository.update(entity);
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
        post.setActive(entity.isActive());
        post.setCommentBlocked(entity.isCommentBlocked());
        post.setCreatedDate(DateTimeUtils.formatDate(entity.getCreatedDate(), DateTimeUtils.DD_MM_YYYY));
        post.setLastModifiedDate(DateTimeUtils.formatDate(entity.getLastModifiedDate(), DateTimeUtils.DD_MM_YYYY));

        post.setComments(new ArrayList<>());

        if (entity.getComments() != null && entity.getComments().size() > 0) {
            entity.getComments().forEach(commentEntity -> {
                Comment comment = new Comment();
                setComment(commentEntity, comment);
                post.getComments().add(comment);
            });
        }

        return post;
    }

    private void setComment(CommentEntity entity, Comment model) {
        model.setId(entity.getId());
        model.setCommentBy(entity.getCommentBy());
        model.setCreatedDate(DateTimeUtils.formatDate(entity.getCreatedDate(), DateTimeUtils.DD_MM_YYYY));
        model.setContent(entity.getContent());
        model.setChildComments(new ArrayList<>());
        if (entity.getChildComments() != null && entity.getChildComments().size() > 0) {
            entity.getChildComments().forEach(commentEntity -> {
                Comment comment = new Comment();
                setComment(commentEntity, comment);
                model.getChildComments().add(comment);
            });
        }
    }

    @Override
    public boolean checkOwnership(String username, Integer postId) {
        AccountEntity account = accountRepository.getByUsername(username);
        PostEntity post = postRepository.read(postId);
        return post.getAuthor().getId().equals(account.getAuthor().getId());
    }

    @Override
    public Post getByCode(String code) {
        PostEntity postEntity = postRepository.getByCode(code);
        if (postEntity != null) return convertToModel(postEntity);
        return null;
    }

    @Override
    public void addChildComment(Integer parentCommentId, Comment comment) {
        CommentEntity commentEntity = commentRepository.read(parentCommentId);
        if (commentEntity != null) {
            CommentEntity entity = new CommentEntity();
            entity.setCommentBy(comment.getCommentBy());
            entity.setContent(comment.getContent());
            entity.setAccepted(false);
            entity.setRemoved(false);
            commentEntity.getChildComments().add(entity);
            entity.setParentComment(commentEntity);
            commentRepository.update(commentEntity);
        }
    }

    @Override
    public void addComment(Integer postId, Comment comment) {
        PostEntity postEntity = postRepository.read(postId);
        if (postEntity != null) {
            CommentEntity entity = new CommentEntity();
            entity.setCommentBy(comment.getCommentBy());
            entity.setContent(comment.getContent());
            entity.setAccepted(false);
            entity.setRemoved(false);
            postEntity.getComments().add(entity);
            entity.setPost(postEntity);
            postRepository.update(postEntity);
        }
    }

    @Override
    public SearchResponse<Post> search(SearchRequest searchRequest) {
        SearchResponse<Post> response = new SearchResponse<>();
        List<PostEntity> entities = repository.search(searchRequest);
        response.setData(entities.stream().map(entity -> {
            Post post = new Post();
            post.setCode(entity.getCode());
            post.setTitle(entity.getTitle());
            return post;
        }).collect(Collectors.toList()));
        response.setDraw(searchRequest.getDraw());
        response.setRecordsFiltered(repository.countSearchRecords(searchRequest));
        response.setRecordsTotal(repository.countAllRecords());
        return response;
    }
}
