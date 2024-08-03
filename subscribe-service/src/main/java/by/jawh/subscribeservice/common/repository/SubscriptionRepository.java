package by.jawh.subscribeservice.common.repository;

import by.jawh.subscribeservice.common.entity.SubscriptionEntity;
import by.jawh.subscribeservice.common.entity.SubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, SubscriptionId> {

    @Query("select s.id.subscriberId " +
            "from SubscriptionEntity s" +
            " where s.id.userId = :userId")
    List<Long> getSubscribers(Long userId);

    @Query("select s.id.userId " +
            "from SubscriptionEntity s " +
            "where s.id.subscriberId = :subscriberId")
    List<Long> getPublishers(Long subscriberId);
}
