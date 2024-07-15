package by.jawh.authmicroservice.business.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestLoginDto {

        @Size(min = 4, max = 24, message = "Имя пользователя должно быть от 4 до 24 символов")
        @NotBlank(message = "Имя пользователя не может быть пустым")
        private String username;

        @Size(min = 8, max = 20, message = "Длина пароля должна быть от 8 до 20 символов")
        @NotBlank(message = "Пароль не может быть пустым")
        private String password;

}
