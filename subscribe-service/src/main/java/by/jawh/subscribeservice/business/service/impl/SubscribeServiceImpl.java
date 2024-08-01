package by.jawh.subscribeservice.business.service.impl;

import by.jawh.eventsforalltopics.events.UserRegisteredSubscribeEvent;
import by.jawh.subscribeservice.business.service.SubscribeService;
import by.jawh.subscribeservice.common.entity.ProfileEntity;
import by.jawh.subscribeservice.common.entity.UserEntity;
import by.jawh.subscribeservice.common.repository.ProfileRepository;
import by.jawh.subscribeservice.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.ProviderNotFoundException;
import java.util.Set;
import java.util.TreeSet;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SubscribeServiceImpl implements SubscribeService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final AuthorizeService authorizeService;

    @Transactional
    @Override
    public void saveProfile(UserRegisteredSubscribeEvent userRegisteredSubscribeEvent) {

        userRepository.save(UserEntity
                .builder()
                .id(userRegisteredSubscribeEvent.getId())
                .build());

        profileRepository.saveAndFlush(ProfileEntity
                .builder()
                .userId(userRegisteredSubscribeEvent.getId())
                .subscriber(new TreeSet<>())
                .publisher(new TreeSet<>())
                .build());
    }

    @Transactional
    @Override
    public boolean subscribe(String token, Long publisherId) {

        Long subscriberId = authorizeService.getProfileIdFromJwt(token);

        // * проверка есть ли такой пользователь
        userRepository.findById(publisherId)
                .orElseThrow(() ->
                        new ProviderNotFoundException("profile with id: %s not found".formatted(publisherId)));

        profileRepository.addSubscriber(subscriberId, publisherId);
        profileRepository.addPublisher(subscriberId, publisherId);

        profileRepository.flush();
        return true;
    }

    @Transactional
    @Override
    public boolean unsubscribe(String token, Long publisherId) {

        Long subscriberId = authorizeService.getProfileIdFromJwt(token);

        UserEntity publisherUser = userRepository.findById(publisherId)
                .orElseThrow(() ->
                        new ProviderNotFoundException("profile with id: %s not found".formatted(publisherId)));
        UserEntity subscriberUser = userRepository.findById(subscriberId)
                .orElseThrow(() ->
                        new ProviderNotFoundException("profile with id: %s not found".formatted(subscriberId)));

        profileRepository.findById(subscriberId)
                .map(profile -> profile.getPublisher().remove(publisherUser));

        profileRepository.findById(publisherId)
                .map(profile -> profile.getSubscriber().remove(subscriberUser));

        profileRepository.flush();
        return true;
    }

    @Override
    public Set<UserEntity> getSubscribers(Long profileId) {
        return profileRepository.findById(profileId)
                .map(ProfileEntity::getSubscriber)
                .orElseThrow(() ->
                        new ProviderNotFoundException("profile with id: %s not found".formatted(profileId)));
    }

    @Override
    public Set<UserEntity> getPublishers(Long profileId) {
        return profileRepository.findById(profileId)
                .map(ProfileEntity::getPublisher)
                .orElseThrow(() ->
                        new ProviderNotFoundException("profile with id: %s not found".formatted(profileId)));
    }

    @Override
    public ProfileEntity findByCurrentUser(String token) {
        Long profileId = authorizeService.getProfileIdFromJwt(token);
        return profileRepository.findById(profileId)
                .orElseThrow(() ->
                        new ProviderNotFoundException("profile with id: %s not found".formatted(profileId)));
    }

    public ProfileEntity findById(Long profileId) {
        return profileRepository.findById(profileId)
                .orElseThrow(() ->
                        new ProviderNotFoundException("profile with id: %s not found".formatted(profileId)));
    }


}
