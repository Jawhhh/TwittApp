package by.jawh.profilemicroservice.common.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "profile")
public class ProfileEntity {

    @Id
    private Long id;

    private String firstname;

    private String lastname;

    private String email;

    private LocalDate bornDate;

    private String status;

    private String avatarUrl;

}
