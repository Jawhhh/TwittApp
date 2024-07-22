package by.jawh.emailmicro.business.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserMailSendDto {

    private String firstname;
    private String lastname;
    private String username;
    private String emailTo;
}
