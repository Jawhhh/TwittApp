package by.jawh.newsfeedservice.business.kafka;

import by.jawh.eventsforalltopics.events.PostForNewsfeedEvent;
import by.jawh.newsfeedservice.business.mapper.NewsfeedMapper;
import by.jawh.newsfeedservice.business.service.NewsfeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_KEY;

@Slf4j
@Component
@RequiredArgsConstructor
public class PostForNewsfeedHandler {

    private final NewsfeedService newsfeedService;

    @KafkaListener(topics = "post-for-newsfeed-events-topic", groupId = "newsfeed-service-group")
    public void newsfeedHandler(@Payload PostForNewsfeedEvent postForNewsfeedEvent,
                                @Header(RECEIVED_KEY) String messageKey) {

        newsfeedService.savePost(postForNewsfeedEvent);
        log.info("post with id: %s saved in redis cache".formatted(postForNewsfeedEvent.getId()));
    }
}
