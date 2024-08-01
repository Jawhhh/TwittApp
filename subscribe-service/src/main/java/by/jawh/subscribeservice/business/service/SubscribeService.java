package by.jawh.subscribeservice.business.service;

import by.jawh.eventsforalltopics.events.UserRegisteredSubscribeEvent;
import by.jawh.subscribeservice.common.entity.ProfileEntity;
import by.jawh.subscribeservice.common.entity.UserEntity;

import java.util.Set;

public interface SubscribeService {

    void saveProfile(UserRegisteredSubscribeEvent userRegisteredSubscribeEvent);

    boolean subscribe(String token, Long publisherId);

    boolean unsubscribe(String token, Long publisherId);

    Set<UserEntity> getSubscribers(Long profileId);

    Set<UserEntity> getPublishers(Long profileId);

    ProfileEntity findByCurrentUser(String token);
}
