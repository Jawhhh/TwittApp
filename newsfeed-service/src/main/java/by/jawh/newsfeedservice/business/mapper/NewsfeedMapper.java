package by.jawh.newsfeedservice.business.mapper;

import by.jawh.eventsforalltopics.events.PostForNewsfeedEvent;
import by.jawh.newsfeedservice.common.entity.Post;
import by.jawh.newsfeedservice.common.entity.PostEs;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface NewsfeedMapper {

    PostEs redisToElasticsearch(Post post);

    Post elasticsearchToRedis(PostEs postEs);

    Post eventToRedisPost(PostForNewsfeedEvent postForNewsfeedEvent);

    PostEs eventToElasticsearchPost(PostForNewsfeedEvent postForNewsfeedEvent);
}
