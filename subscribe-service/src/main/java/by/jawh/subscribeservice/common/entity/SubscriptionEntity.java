package by.jawh.subscribeservice.common.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subscriptions")
public class SubscriptionEntity {

    @EmbeddedId
    private SubscriptionId id;

    @ManyToOne
    @MapsId("followerId")
    @JoinColumn(name = "follower_id")
    private UserEntity subscriber;

    @ManyToOne
    @MapsId("followeeId")
    @JoinColumn(name = "followee_id")
    private UserEntity publisher;
}


