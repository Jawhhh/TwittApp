package by.jawh.authmicroservice.business.dto;

import by.jawh.authmicroservice.common.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class UserRequestRegisterDto {

    @Size(min = 2, max = 26, message = "Имя должно быть от 2 до 26 символов")
    @NotBlank(message = "Имя не может быть пустым")
    private String firstname;

    @Size(min = 2, max = 26, message = "Имя должно быть от 2 до 26 символов")
    @NotBlank(message = "Фамилия не может быть пустой")
    private String lastname;

    @Email(message = "Email адрес должен быть в формате user@example.com")
    @NotBlank(message = "Адрес электронной почты не может быть пустыми")
    private String email;

    @Past(message = "Дата рождения должна быть в прошлом")
    private LocalDate bornDate;

    @Size(min = 8, max = 20, message = "Длина пароля должна быть от 8 до 20 символов")
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;

    @Size(min = 4, max = 24, message = "Имя пользователя должно быть от 4 до 24 символов")
    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String username;
}
