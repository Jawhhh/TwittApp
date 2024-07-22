package by.jawh.authmicroservice.business.service;

import by.jawh.authmicroservice.business.dto.ProfileForEventDto;
import by.jawh.authmicroservice.business.dto.UserRequestLoginDto;
import by.jawh.authmicroservice.business.dto.UserRequestRegisterDto;
import by.jawh.authmicroservice.common.entity.UserEntity;
import by.jawh.eventsforalltopics.events.UserNotificationEvent;
import by.jawh.eventsforalltopics.events.UserRegisteredEvent;
import by.jawh.eventsforalltopics.events.UserRegisteredSubscribeEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final RestTemplate restTemplate;

    public void userRegisterSendEvent(UserEntity userEntity,
                                      UserRequestRegisterDto userRequestRegisterDto) {

        ProducerRecord<String, Object> profileRecord = new ProducerRecord<>(
                "user-registered-events-topic",
                userEntity.getId().toString(),
                new UserRegisteredEvent(
                        userEntity.getId(),
                        userRequestRegisterDto.getFirstname(),
                        userRequestRegisterDto.getLastname(),
                        userRequestRegisterDto.getEmail(),
                        userRequestRegisterDto.getBornDate()
                ));
        profileRecord.headers().add("messageId", UUID.randomUUID().toString().getBytes());
        kafkaTemplate.send(profileRecord);
    }

    public void userRegisterNotificationSendEvent(UserEntity userEntity,
                                                  UserRequestRegisterDto userRequestRegisterDto) {


        ProducerRecord<String, Object> notificationRecord = new ProducerRecord<>(
                "user-registered-notification-events-topic",
                userEntity.getUsername(),
                new UserNotificationEvent(
                        userRequestRegisterDto.getFirstname(),
                        userRequestRegisterDto.getLastname(),
                        userRequestRegisterDto.getEmail(),
                        userRequestRegisterDto.getUsername()
                ));
        notificationRecord.headers().add("messageId", UUID.randomUUID().toString().getBytes());
        kafkaTemplate.send(notificationRecord);
    }

    public void userLoginNotificationSendEvent(UserRequestLoginDto userRequestLoginDto, URI url) {

        ProfileForEventDto profileInfo =
                restTemplate.getForEntity(url, ProfileForEventDto.class).getBody();

        ProducerRecord<String, Object> notificationRecord = new ProducerRecord<>(
                "user-registered-notification-events-topic",
                userRequestLoginDto.getUsername(),
                new UserNotificationEvent(
                        profileInfo.getFirstname(),
                        profileInfo.getLastname(),
                        profileInfo.getEmail(),
                        userRequestLoginDto.getUsername()
                ));

        notificationRecord.headers().add("messageId", UUID.randomUUID().toString().getBytes());
        kafkaTemplate.send(notificationRecord);
    }

    public void userRegisteredSubscribeSendEvent(Long id) {

        ProducerRecord<String, Object> subscribeRecord = new ProducerRecord<>(
                "user-registered-subscribe-events-topic",
                id.toString(),
                new UserRegisteredSubscribeEvent(id)
        );

        subscribeRecord.headers().add("messageId", UUID.randomUUID().toString().getBytes());
        kafkaTemplate.send(subscribeRecord);
    }
}
