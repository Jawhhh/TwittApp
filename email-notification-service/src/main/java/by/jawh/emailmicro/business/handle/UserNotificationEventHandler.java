package by.jawh.emailmicro.business.handle;

import by.jawh.emailmicro.business.service.impl.MailSenderServiceImpl;
import by.jawh.emailmicro.mapper.EventToDtoMapper;
import by.jawh.eventsforalltopics.events.UserNotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserNotificationEventHandler {

    private final MailSenderServiceImpl mailSenderService;
    private final EventToDtoMapper eventToDtoMapper;

    @KafkaListener(topics = "user-registered-notification-events-topic")
    public void handleRegistered(UserNotificationEvent userNotificationEvent) {
        log.info("handleLogin kafka message for send email notification about user registration");
        mailSenderService.registrationSend(eventToDtoMapper.eventToDto(userNotificationEvent));
    }

    @KafkaListener(topics = "user-login-notification-events-topic")
    public void handleLogin(UserNotificationEvent userNotificationEvent) {
        log.info("handleLogin kafka message for send email notification about user login");
        mailSenderService.loginSend(eventToDtoMapper.eventToDto(userNotificationEvent));
    }
}
