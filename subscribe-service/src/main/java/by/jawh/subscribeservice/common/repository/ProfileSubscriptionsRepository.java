package by.jawh.subscribeservice.common.repository;

import by.jawh.subscribeservice.common.entity.ProfileSubscriptionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileSubscriptionsRepository extends JpaRepository<ProfileSubscriptionsEntity, Long> {
}
