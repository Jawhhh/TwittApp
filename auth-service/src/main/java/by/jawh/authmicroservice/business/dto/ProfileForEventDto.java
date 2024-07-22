package by.jawh.authmicroservice.business.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class ProfileForEventDto {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private LocalDate bornDate;
    private String avatarUrl;
    private String status;
}
