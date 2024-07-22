package by.jawh.postservice.business.service.util;

import by.jawh.eventsforalltopics.events.PostForNewsfeedEvent;
import by.jawh.postservice.common.entity.PostEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendNewsfeedEvent(PostEntity postEntity) {
        ProducerRecord<String, Object> newsfeedRecord = new ProducerRecord<>(
                "post-for-newsfeed-events-topic",
                postEntity.getId().toString(),
                new PostForNewsfeedEvent(
                        postEntity.getId(),
                        postEntity.getText(),
                        postEntity.getPictureUrl(),
                        postEntity.getProfileId(),
                        postEntity.getTimePublication(),
                        postEntity.getLike().size(),
                        postEntity.getDislike().size(),
                        postEntity.getComment().size()
                ));
        kafkaTemplate.send(newsfeedRecord);
        log.info("send event in post-for-newsfeed-events-topic");
    }
}
