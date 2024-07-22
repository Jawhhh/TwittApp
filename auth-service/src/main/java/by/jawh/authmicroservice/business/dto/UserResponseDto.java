package by.jawh.authmicroservice.business.dto;

import by.jawh.authmicroservice.common.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {

    private Long id;

    private String username;

    private Role role;

}
