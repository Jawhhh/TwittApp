package by.jawh.subscribeservice.business.service;

import by.jawh.eventsforalltopics.events.UserRegisteredSubscribeEvent;
import by.jawh.subscribeservice.common.entity.ProfileSubscriptionsEntity;

public interface SubscribeService {

    ProfileSubscriptionsEntity saveProfile(UserRegisteredSubscribeEvent userRegisteredSubscribeEvent);

    ProfileSubscriptionsEntity subscribe(Long profileId, Long subscriberId);

    ProfileSubscriptionsEntity unsubscribe(Long profileId, Long unsubscriberId);

    ProfileSubscriptionsEntity findById(Long profileId);
}
