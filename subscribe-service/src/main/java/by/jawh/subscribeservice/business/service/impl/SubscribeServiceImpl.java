package by.jawh.subscribeservice.business.service.impl;

import by.jawh.eventsforalltopics.events.UserRegisteredSubscribeEvent;
import by.jawh.subscribeservice.business.mapper.SubscribeMapper;
import by.jawh.subscribeservice.business.service.SubscribeService;
import by.jawh.subscribeservice.common.entity.ProfileSubscriptionsEntity;
import by.jawh.subscribeservice.common.repository.ProfileSubscriptionsRepository;
import by.jawh.subscribeservice.exception.ProfileAlreadyExistsException;
import by.jawh.subscribeservice.exception.ProfileNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscribeServiceImpl implements SubscribeService {

    private final ProfileSubscriptionsRepository profileSubscriptionsRepository;
    private final SubscribeMapper subscribeMapper;




    @Override
    public ProfileSubscriptionsEntity saveProfile(UserRegisteredSubscribeEvent userRegisteredSubscribeEvent) {
        if (profileSubscriptionsRepository.findById(userRegisteredSubscribeEvent.getId()).isPresent()) {
            throw new ProfileAlreadyExistsException(
                    "profile with id: %s already exists".formatted(userRegisteredSubscribeEvent.getId()));
        } else {
            ProfileSubscriptionsEntity profileSubscriptionsEntity =
                    subscribeMapper.eventToEntity(userRegisteredSubscribeEvent);

            return profileSubscriptionsRepository.save(profileSubscriptionsEntity);
        }
    }

    @Override
    public ProfileSubscriptionsEntity subscribe(Long profileId, Long subscriberId) {

        ProfileSubscriptionsEntity profileForSubEntity = profileSubscriptionsRepository
                .findById(profileId)
                .orElseThrow(() -> new ProfileNotFoundException(
                        "profile with id: %s not found".formatted(profileId)));

        ProfileSubscriptionsEntity subscriberEntity = profileSubscriptionsRepository
                .findById(subscriberId)
                .orElseThrow(() -> new ProfileNotFoundException(
                        "profile with id: %s not found".formatted(subscriberId)
                ));

        // * add subscriber in need profile
        Map<Long, Long> subscribersIds = profileForSubEntity.getSubscribersIds();
        subscribersIds.put(subscriberId, subscriberId);
        profileForSubEntity.setSubscribersIds(subscribersIds);

        // * add subscription in subscriber
        Map<Long, Long> subscriptionsIds = subscriberEntity.getSubscriptionsIds();
        subscriptionsIds.put(profileId, profileId);
        subscriberEntity.setSubscribersIds(subscriptionsIds);

        log.info("user with id: %s subscribed on user with id: %s"
                .formatted(subscriberId, profileId));
        return subscriberEntity;
    }

    @Override
    public ProfileSubscriptionsEntity unsubscribe(Long profileId, Long unsubscriberId) {
        ProfileSubscriptionsEntity profileForUnsubEntity = profileSubscriptionsRepository
                .findById(profileId)
                .orElseThrow(() -> new ProfileNotFoundException(
                        "profile with id: %s not found".formatted(profileId)
                ));

        ProfileSubscriptionsEntity unsubscriberEntity = profileSubscriptionsRepository.
                findById(unsubscriberId)
                .orElseThrow(() -> new ProfileNotFoundException(
                        "profile with id: %s not found".formatted(unsubscriberId)
                ));

        Map<Long, Long> subscribersIds = profileForUnsubEntity.getSubscribersIds();
        subscribersIds.remove(unsubscriberId);
        profileForUnsubEntity.setSubscribersIds(subscribersIds);


        Map<Long, Long> subscriptionsIds = unsubscriberEntity.getSubscriptionsIds();
        subscriptionsIds.remove(profileId);
        unsubscriberEntity.setSubscriptionsIds(subscriptionsIds);

        log.info("user with id: %s unsubscribed on user with id: %s"
                .formatted(unsubscriberId, profileId));
        return unsubscriberEntity;
    }

    @Override
    public ProfileSubscriptionsEntity findById(Long profileId) {
        return profileSubscriptionsRepository.findById(profileId)
                .orElseThrow(() -> new ProfileNotFoundException(
                        "profile with id: %s not found".formatted(profileId)
                ));
    }
}
