package by.jawh.newsfeedservice.business.service;

import by.jawh.eventsforalltopics.events.PostForNewsfeedEvent;
import by.jawh.newsfeedservice.business.mapper.NewsfeedMapper;
import by.jawh.newsfeedservice.common.entity.Post;
import by.jawh.newsfeedservice.common.entity.PostEs;
import by.jawh.newsfeedservice.common.repository.PostElasticsearchRepository;
import by.jawh.newsfeedservice.common.repository.PostRedisRepository;
import by.jawh.newsfeedservice.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsfeedService {

    private final PostRedisRepository postRedisRepository;
    private final PostElasticsearchRepository postElasticsearchRepository;
    private final NewsfeedMapper newsfeedMapper;

    public void savePost(PostForNewsfeedEvent postEvent) {

        Post postRedis = newsfeedMapper.eventToRedisPost(postEvent);
        postRedisRepository.save(postRedis);
        postElasticsearchRepository.save(newsfeedMapper.redisToElasticsearch(postRedis));
    }

    public Post getPostById(Long id) {
        return postRedisRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("post with id: %s not found".formatted(id)));
    }

    public Page<Post> getPostByProfileId(Long profileId, Pageable pageable) {

        Page<PostEs> postEsPage = postElasticsearchRepository.findByProfileId(profileId, pageable);
        List<Post> posts = postEsPage.stream()
                .map(postEs -> postRedisRepository.findById(postEs.getId()).orElse(null))
                .toList();

        return new PageImpl<>(posts, pageable, postEsPage.getTotalElements());
    }

    public Page<Post> getAllPost(Pageable pageable) {
        Page<PostEs> postEsPage = postElasticsearchRepository.findAll(pageable);
        List<Post> posts = postEsPage.stream()
                .map(postEs -> postRedisRepository.findById(postEs.getId()).orElse(null))
                .toList();
        return new PageImpl<>(posts, pageable, postEsPage.getTotalElements());
    }

}
