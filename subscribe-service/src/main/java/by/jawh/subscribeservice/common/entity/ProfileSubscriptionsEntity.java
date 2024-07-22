package by.jawh.subscribeservice.common.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profile_subscribers")
public class ProfileSubscriptionsEntity {

    @Id
    private Long profileId;

    @ElementCollection
    public Map<Long, Long> subscribersIds = new HashMap<>();

    @ElementCollection
    private Map<Long, Long> subscriptionsIds = new HashMap<>();
}
