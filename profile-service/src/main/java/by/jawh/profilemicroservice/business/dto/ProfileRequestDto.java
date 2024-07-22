package by.jawh.profilemicroservice.business.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProfileRequestDto {

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
    @NotBlank(message = "Дата рождения не может быть пустой")
    private LocalDate bornDate;

    @Size(max = 100, message = "Статус должен быть не большее 100 символов")
    private String status;
}

