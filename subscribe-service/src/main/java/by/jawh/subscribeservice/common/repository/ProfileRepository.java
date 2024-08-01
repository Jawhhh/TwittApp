package by.jawh.subscribeservice.common.repository;

import by.jawh.subscribeservice.common.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {

    @Query("select p from ProfileEntity p where p.userId = :id")
    Optional<ProfileEntity> findById(Long id);

    @Query("update ProfileEntity p " +
           "set p.publisher = :publisherId " +
           "where p.userId = :subscriberId")
    void addPublisher(Long subscriberId, Long publisherId);

    @Query("update ProfileEntity p " +
           "set p.subscriber = :subscriberId " +
           "where p.userId = :publisherId")
    void addSubscriber(Long subscriberId, Long publisherId);
}
