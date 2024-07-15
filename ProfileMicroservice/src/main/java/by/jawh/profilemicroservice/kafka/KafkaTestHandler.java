package by.jawh.profilemicroservice.kafka;

import by.jawh.eventsforalltopics.events.UserRegisteredEvent;
import by.jawh.profilemicroservice.common.entity.ProfileEntity;
import by.jawh.profilemicroservice.common.repository.MessageRepository;
import by.jawh.profilemicroservice.common.repository.ProfileRepository;
import by.jawh.profilemicroservice.business.mapper.ProfileMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@KafkaListener(topics = "user-registered-events-topic", groupId = "profile-service-group")
public class KafkaTestHandler {

    private final MessageRepository messageRepository;
    private final ProfileMapper profileMapper;
    private final ProfileRepository profileRepository;


    @KafkaHandler
    public void UserRegisteredHandle(@Payload UserRegisteredEvent userRegisteredEvent,
                                                   @Header("messageId") String messageId) {

        if (messageRepository.findById(messageId).isPresent()) {
            log.info("kafka message with message id: %s has already been processed".formatted(messageId));
            return;
        }

        ProfileEntity profileEntity = profileMapper.eventToEntity(userRegisteredEvent);
        profileRepository.saveAndFlush(profileEntity);
    }
}
