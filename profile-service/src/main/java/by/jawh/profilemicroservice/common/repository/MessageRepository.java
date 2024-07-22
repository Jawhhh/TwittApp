package by.jawh.profilemicroservice.common.repository;

import by.jawh.profilemicroservice.common.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MessageRepository extends JpaRepository<MessageEntity, String> {

    Optional<MessageEntity> findById(String id);
}

