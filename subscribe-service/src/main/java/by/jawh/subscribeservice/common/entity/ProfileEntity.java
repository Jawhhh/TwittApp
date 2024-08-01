package by.jawh.subscribeservice.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Set;
import java.util.TreeSet;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profile")
public class ProfileEntity {

    @Id
    private Long userId;

    @OneToMany(mappedBy = "id", orphanRemoval = true)
    Set<UserEntity> subscriber = new TreeSet<>();

    @OneToMany(mappedBy = "id", orphanRemoval = true)
    Set<UserEntity> publisher = new TreeSet<>();
}
