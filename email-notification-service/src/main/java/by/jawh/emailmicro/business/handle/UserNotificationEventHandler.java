package by.jawh.emailmicro.business.handle;

import by.jawh.emailmicro.business.service.impl.MailSenderServiceImpl;
import by.jawh.emailmicro.business.mapper.EmailMapper;
import by.jawh.eventsforalltopics.events.UserNotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserNotificationEventHandler {

    private final MailSenderServiceImpl mailSenderService;
    private final EmailMapper emailMapper;

    @KafkaListener(topics = "user-registered-notification-events-topic")
    public void handleRegistered(@Payload UserNotificationEvent userNotificationEvent) {

        log.info("handleLogin kafka message for send email notification about user registration");
        mailSenderService.registrationSend(emailMapper.eventToDto(userNotificationEvent));
    }

    @KafkaListener(topics = "user-login-notification-events-topic")
    public void handleLogin(@Payload UserNotificationEvent userNotificationEvent) {

        log.info("handleLogin kafka message for send email notification about user login");
        mailSenderService.loginSend(emailMapper.eventToDto(userNotificationEvent));
    }
}
