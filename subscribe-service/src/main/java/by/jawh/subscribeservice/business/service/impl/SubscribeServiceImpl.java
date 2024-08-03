package by.jawh.subscribeservice.business.service.impl;

import by.jawh.eventsforalltopics.events.UserRegisteredSubscribeEvent;
import by.jawh.subscribeservice.business.service.SubscribeService;
import by.jawh.subscribeservice.common.entity.SubscriptionEntity;
import by.jawh.subscribeservice.common.entity.SubscriptionId;
import by.jawh.subscribeservice.common.entity.UserEntity;
import by.jawh.subscribeservice.common.repository.SubscriptionRepository;
import by.jawh.subscribeservice.common.repository.UserRepository;
import by.jawh.subscribeservice.exception.ProfileNotFoundException;
import by.jawh.subscribeservice.exception.SubscriptionNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.ProviderNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SubscribeServiceImpl implements SubscribeService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final AuthorizeService authorizeService;

    @Transactional
    @Override
    public void saveProfile(UserRegisteredSubscribeEvent userRegisteredSubscribeEvent) {

        UserEntity user = UserEntity.builder()
                .id(userRegisteredSubscribeEvent.getId())
                .subscribers(new HashSet<>())
                .subscriptions(new HashSet<>())
                .build();
        userRepository.saveAndFlush(user);
    }

    @Transactional
    @Override
    public boolean subscribe(String token, Long publisherId) {
        Long subscriberId = authorizeService.getProfileIdFromJwt(token);

        UserEntity publisher = userRepository.findById(publisherId).orElseThrow();
        UserEntity subscriber = userRepository.findById(subscriberId).orElseThrow();

        SubscriptionEntity subscription = new SubscriptionEntity();
        subscription.setId(new SubscriptionId(publisherId, subscriberId));
        subscription.setUser(publisher);
        subscription.setSubscriber(subscriber);
        subscriptionRepository.saveAndFlush(subscription);

        return true;
    }

    @Transactional
    @Override
    public boolean unsubscribe(String token, Long publisherId) {

        Long subscriberId = authorizeService.getProfileIdFromJwt(token);

        UserEntity publisher = userRepository.findById(publisherId)
                .orElseThrow(() ->
                        new ProfileNotFoundException("user with id: %s not found".formatted(publisherId)));
        UserEntity subscriber = userRepository.findById(subscriberId)
                .orElseThrow(() ->
                        new ProfileNotFoundException("user with id: %s not found".formatted(publisherId)));

        SubscriptionEntity subscriptionEntity = subscriptionRepository
                .findById(new SubscriptionId(publisherId, subscriberId))
                .orElseThrow(() -> new SubscriptionNotFoundException(
                        "subscription with publisher: %s and subscriber: %s not found"
                                .formatted(publisherId, subscriberId)));

        subscriptionRepository.delete(subscriptionEntity);
        subscriptionRepository.flush();

        return true;
    }

    @Override
    public List<Long> getSubscribers(Long id) {
        return subscriptionRepository.getSubscribers(id);
    }

    @Override
    public List<Long> getPublishers(Long id) {
        return subscriptionRepository.getPublishers(id);
    }

    @Override
    public UserEntity findByCurrentUser(String token) {
        Long id = authorizeService.getProfileIdFromJwt(token);
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ProfileNotFoundException("user with id: %s not found".formatted(id)));
    }

    @Override
    public UserEntity findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ProfileNotFoundException("user with id: %s not found".formatted(id)));
    }


}
