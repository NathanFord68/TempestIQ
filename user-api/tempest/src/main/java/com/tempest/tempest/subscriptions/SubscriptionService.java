package com.tempest.tempest.subscriptions;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

    private final SubscriptionRepository repository;    

    public SubscriptionService(SubscriptionRepository repository) {
        this.repository = repository;
    }

    List<SubscriptionEntity> getAllSubscriptions(String username) throws Exception {
        return repository.findAllByUsername(username);
    }
}
