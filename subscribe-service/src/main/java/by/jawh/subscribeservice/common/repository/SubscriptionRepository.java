package by.jawh.subscribeservice.common.repository;

import by.jawh.subscribeservice.common.entity.SubscriptionEntity;
import by.jawh.subscribeservice.common.entity.SubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, SubscriptionId> {

    List<SubscriptionEntity> findBySubscriberId(Long followerId);

    List<SubscriptionEntity> findByPublisherId(Long followeeId);
}

