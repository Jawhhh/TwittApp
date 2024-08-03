package by.jawh.subscribeservice.common.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private SubscriptionId id = new SubscriptionId();

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserEntity user;

    @ManyToOne
    @MapsId("subscriberId")
    @JoinColumn(name = "subscriber_id")
    @JsonBackReference
    private UserEntity subscriber;
}
