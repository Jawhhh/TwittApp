package by.jawh.newsfeedservice.business.service;

import by.jawh.eventsforalltopics.events.PostForNewsfeedEvent;
import by.jawh.newsfeedservice.business.mapper.NewsfeedMapper;
import by.jawh.newsfeedservice.common.entity.Post;
import by.jawh.newsfeedservice.common.entity.PostEs;
import by.jawh.newsfeedservice.common.repository.NewsfeedElasticsearchRepository;
import by.jawh.newsfeedservice.common.repository.NewsfeedRedisRepository;
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

    private final NewsfeedRedisRepository newsfeedRedisRepository;
    private final NewsfeedElasticsearchRepository newsfeedElasticsearchRepository;
    private final NewsfeedMapper newsfeedMapper;

    public void savePost(PostForNewsfeedEvent postEvent) {

        Post postRedis = newsfeedMapper.eventToRedisPost(postEvent);
        newsfeedRedisRepository.save(postRedis);
        newsfeedElasticsearchRepository.save(newsfeedMapper.redisToElasticsearch(postRedis));
    }

    public Post getPostById(Long id) {
        return newsfeedRedisRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("post with id: %s not found".formatted(id)));
    }

    public Page<Post> getPostByProfileId(Long profileId, Pageable pageable) {

        Page<PostEs> postEsPage = newsfeedElasticsearchRepository.findByProfileId(profileId, pageable);
        List<Post> posts = postEsPage.stream()
                .map(postEs -> newsfeedRedisRepository.findById(postEs.getId()).orElse(null))
                .toList();

        return new PageImpl<>(posts, pageable, postEsPage.getTotalElements());
    }

    public Page<Post> getAllPost(Pageable pageable) {
        Page<PostEs> postEsPage = newsfeedElasticsearchRepository.findAll(pageable);
        List<Post> posts = postEsPage.stream()
                .map(postEs -> newsfeedRedisRepository.findById(postEs.getId()).orElse(null))
                .toList();
        return new PageImpl<>(posts, pageable, postEsPage.getTotalElements());
    }
}
