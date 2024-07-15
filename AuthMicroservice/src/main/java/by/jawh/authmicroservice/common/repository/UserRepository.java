package by.jawh.authmicroservice.common.repository;

import by.jawh.authmicroservice.common.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findAllByUsernameAndPassword(String username, String password);

    Optional<UserEntity> findByUsername(String username);
}
