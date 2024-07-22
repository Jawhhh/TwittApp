package by.jawh.subscribeservice.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "kafka_messages")
public class kafkaMessagesEntity {

    @Id
    @Column(name = "id")
    private String messageId;
}
