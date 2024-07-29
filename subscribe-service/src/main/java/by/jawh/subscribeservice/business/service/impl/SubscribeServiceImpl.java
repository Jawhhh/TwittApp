package by.jawh.subscribeservice.business.service.impl;

import by.jawh.eventsforalltopics.events.UserRegisteredSubscribeEvent;
import by.jawh.subscribeservice.business.mapper.SubscribeMapper;
import by.jawh.subscribeservice.business.service.SubscribeService;
import by.jawh.subscribeservice.exception.ProfileAlreadyExistsException;
import by.jawh.subscribeservice.exception.ProfileNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SubscribeServiceImpl implements SubscribeService {

    private final ProfileRepository profileRepository;
    private final SubscriberRepository subscriberRepository;
    private final PublisherRepository publisherRepository;
    private final SubscribeMapper subscribeMapper;
    private final AuthorizeService authorizeService;



    @Transactional
    @Override
    public ProfileEntity saveProfile(UserRegisteredSubscribeEvent userRegisteredSubscribeEvent) {
        if (profileRepository.findById(userRegisteredSubscribeEvent.getId()).isPresent()) {
            throw new ProfileAlreadyExistsException(
                    "profile with id: %s already exists".formatted(userRegisteredSubscribeEvent.getId()));
        } else {
            ProfileEntity profileEntity =
                    subscribeMapper.eventToEntity(userRegisteredSubscribeEvent);
            log.info("profile with id: %s was saves".formatted(profileEntity.getProfileId()));
            return profileRepository.save(profileEntity);
        }
    }

    @Transactional
    @Override
    public ProfileEntity subscribe(Long publisherId, String token) {


        PublisherEntity publisher = publisherRepository
                .findById(publisherId)
                .orElseThrow(() -> new ProfileNotFoundException(
                        "profile with id: %s not found".formatted(publisherId)));

        Long profileId = authorizeService.getProfileIdFromJwt(token);

        ProfileEntity profile = profileRepository
                .findById(profileId)
                .orElseThrow(() -> new ProfileNotFoundException(
                        "profile with id: %s not found".formatted(profileId)
                ));

        //* устанавливаем профилю подписчика
        Set<PublisherEntity> publisherSet = profile.getPublisher();
        publisherSet.add(publisher);

        //* устанавливаем профилю подписку
        Set<Long> subscriptionsIds = profile.getSubscriptionsIds();
        subscriptionsIds.add(profileId);

        profileRepository.flush();
        log.info("user with id: %s subscribed on user with id: %s"
                .formatted(profileId, profileId));
        return profile;
    }

    @Transactional
    @Override
    public ProfileEntity unsubscribe(Long profileId, String token) {

        ProfileEntity profileForUnsubEntity = profileRepository
                .findById(profileId)
                .orElseThrow(() -> new ProfileNotFoundException(
                        "profile with id: %s not found".formatted(profileId)
                ));

        Long unsubscriberId = authorizeService.getProfileIdFromJwt(token);

        ProfileEntity unsubscriberEntity = profileRepository.
                findById(unsubscriberId)
                .orElseThrow(() -> new ProfileNotFoundException(
                        "profile with id: %s not found".formatted(unsubscriberId)
                ));

       //* удаляем у профиля подписчика
        Set<Long> subscribersIds = profileForUnsubEntity.getSubscribersIds();
        subscribersIds.remove(unsubscriberId);

        //* удаляем у профиля подписку
        Set<Long> subscriptionsIds = unsubscriberEntity.getSubscriptionsIds();
        subscriptionsIds.remove(profileId);

        profileRepository.flush();
        log.info("user with id: %s unsubscribed on user with id: %s"
                .formatted(unsubscriberId, profileId));
        return unsubscriberEntity;
    }

    public ProfileEntity findByCurrentId(String token) {
        Long profileId = authorizeService.getProfileIdFromJwt(token);
        return profileRepository.findById(profileId)
                .orElseThrow(() -> new ProfileNotFoundException(
                        "profile with id: %s not found".formatted(profileId)
                ));
    }

    public ProfileEntity findById(Long profileId) {
        return profileRepository.findById(profileId)
                .orElseThrow(() -> new ProfileNotFoundException(
                        "profile with id: %s not found".formatted(profileId)
                ));
    }
}
