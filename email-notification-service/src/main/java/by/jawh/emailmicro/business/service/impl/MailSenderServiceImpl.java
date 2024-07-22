package by.jawh.emailmicro.business.service.impl;

import by.jawh.emailmicro.consts.MessageTemplates;
import by.jawh.emailmicro.business.dto.UserMailSendDto;
import by.jawh.emailmicro.business.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSenderServiceImpl implements MailSenderService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Override
    public void registrationSend(UserMailSendDto userMailSendDto) {
        String subject = MessageTemplates.REGISTRATION_SUBJECT;
        String messageBody = MessageTemplates.REGISTRATION_MESSAGE.formatted(
                userMailSendDto.getFirstname(),
                userMailSendDto.getLastname(),
                userMailSendDto.getUsername());

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mailFrom);
        mailMessage.setTo(userMailSendDto.getEmailTo());
        mailMessage.setSubject(subject);
        mailMessage.setText(messageBody);

        javaMailSender.send(mailMessage);
    }

    @Override
    public void loginSend(UserMailSendDto userMailSendDto) {

        String subject = MessageTemplates.LOGIN_SUBJECT;
        String messageBody = MessageTemplates.LOGIN_MESSAGE.formatted(
                userMailSendDto.getFirstname(),
                userMailSendDto.getLastname(),
                userMailSendDto.getUsername());

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mailFrom);
        mailMessage.setTo(userMailSendDto.getEmailTo());
        mailMessage.setSubject(subject);
        mailMessage.setText(messageBody);

        javaMailSender.send(mailMessage);
    }
}
