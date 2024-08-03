package by.jawh.subscribeservice.business.service;

import by.jawh.eventsforalltopics.events.UserRegisteredSubscribeEvent;
import by.jawh.subscribeservice.common.entity.SubscriptionEntity;
import by.jawh.subscribeservice.common.entity.UserEntity;

import java.util.List;
import java.util.Set;

public interface SubscribeService {

    void saveProfile(UserRegisteredSubscribeEvent userRegisteredSubscribeEvent);

    boolean subscribe(String token, Long publisherId);

    boolean unsubscribe(String token, Long publisherId);

    List<Long> getSubscribers(Long profileId);

    List<Long> getPublishers(Long profileId);

    UserEntity findById(Long profileId);

    UserEntity findByCurrentUser(String token);


}
