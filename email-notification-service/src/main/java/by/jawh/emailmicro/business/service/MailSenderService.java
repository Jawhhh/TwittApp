package by.jawh.emailmicro.business.service;

import by.jawh.emailmicro.business.dto.UserMailSendDto;

public interface MailSenderService {

    void registrationSend(UserMailSendDto userMailSendDto);

    void loginSend(UserMailSendDto userMailSendDto);
}
