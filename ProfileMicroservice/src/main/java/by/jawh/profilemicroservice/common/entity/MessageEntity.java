package by.jawh.profilemicroservice.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class MessageEntity {

    @Id
    String id;
}
