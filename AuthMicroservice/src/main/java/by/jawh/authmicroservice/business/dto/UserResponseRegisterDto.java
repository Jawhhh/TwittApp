package by.jawh.authmicroservice.business.dto;

import by.jawh.authmicroservice.common.entity.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserResponseRegisterDto {

    private Long id;

    private String firstname;

    private String lastname;

    private String email;

    private LocalDate bornDate;

    private String username;

    private Role role;

//    private List<PostTheme> interestedPostTheme;
//
//    private List<UserEntity> userSubscribed;
}
