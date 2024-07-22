package by.jawh.subscribeservice.common.repository;

import by.jawh.subscribeservice.common.entity.kafkaMessagesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KafkaMessagesRepository extends JpaRepository<kafkaMessagesEntity, String> {
}
