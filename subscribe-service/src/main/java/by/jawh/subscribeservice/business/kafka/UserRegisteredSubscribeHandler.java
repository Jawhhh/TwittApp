package by.jawh.subscribeservice.business.kafka;

import by.jawh.eventsforalltopics.events.UserRegisteredSubscribeEvent;
import by.jawh.subscribeservice.business.service.impl.SubscribeServiceImpl;
import by.jawh.subscribeservice.common.repository.KafkaMessagesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_KEY;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserRegisteredSubscribeHandler {

    private final SubscribeServiceImpl subscribeService;
    private final KafkaMessagesRepository kafkaMessagesRepository;

    @KafkaListener(topics = "user-registered-subscribe-events-topic", groupId = "subscribe-service-group")
    public void profileCreateHandle(@Payload UserRegisteredSubscribeEvent userRegisteredSubscribeEvent,
                                    @Header("messageId") String messageId,
                                    @Header(RECEIVED_KEY) String messageKey) {


        if (kafkaMessagesRepository.findById(messageId).isPresent()) {
            log.info("kafka message with message id: %s has already been processed".formatted(messageId));
            subscribeService.saveProfile(userRegisteredSubscribeEvent);
        }
    }
}
