package by.jawh.subscribeservice.common.repository;

import by.jawh.subscribeservice.common.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
