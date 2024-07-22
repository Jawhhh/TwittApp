package by.jawh.profilemicroservice.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "kafka_messages")
public class MessageEntity {

    @Id
    String id;
}
