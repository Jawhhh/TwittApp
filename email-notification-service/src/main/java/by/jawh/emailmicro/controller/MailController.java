package by.jawh.emailmicro.controller;

import by.jawh.emailmicro.business.dto.UserMailSendDto;
import by.jawh.emailmicro.business.service.impl.MailSenderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailSenderServiceImpl mailSenderService;

    @PostMapping("/send")
    public ResponseEntity<?> sendMail(@RequestBody UserMailSendDto userMailSendDto) {
        mailSenderService.registrationSend(userMailSendDto);
        return ResponseEntity.ok().build();
    }
}
