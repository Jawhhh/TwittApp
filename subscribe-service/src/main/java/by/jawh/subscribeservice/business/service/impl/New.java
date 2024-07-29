package by.jawh.subscribeservice.business.service.impl;

import by.jawh.subscribeservice.common.entity.SubscriptionEntity;
import by.jawh.subscribeservice.common.entity.SubscriptionId;
import by.jawh.subscribeservice.common.entity.UserEntity;
import by.jawh.subscribeservice.common.repository.SubscriptionRepository;
import by.jawh.subscribeservice.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class New {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final AuthorizeService authorizeService;

    public void subscribe(String token, Long publisherId) {

        Long subscriberId = authorizeService.getProfileIdFromJwt(token);

        UserEntity subscriber = userRepository.findById(subscriberId).orElseThrow();
        UserEntity publisher = userRepository.findById(publisherId).orElseThrow();

        SubscriptionEntity subscription = new SubscriptionEntity();
        subscription.setSubscriber(subscriber);
        subscription.setPublisher(publisher);
        subscriptionRepository.saveAndFlush(subscription);
    }

    public void unsubscribe(String token, Long publisherId) {

        Long subscriberId = authorizeService.getProfileIdFromJwt(token);
        SubscriptionId subscriptionId = new SubscriptionId(subscriberId, publisherId);
        subscriptionRepository.deleteById(subscriptionId);
    }

    public List<UserEntity> getSubscribers(Long userId) {
        return subscriptionRepository.findBySubscriberId(userId)
                .stream()
                .map(SubscriptionEntity::getSubscriber)
                .toList();
    }

    public List<UserEntity> getPublishers(Long userId) {
        return subscriptionRepository.findByPublisherId(userId)
                .stream()
                .map(SubscriptionEntity::getPublisher)
                .toList();
    }










}
