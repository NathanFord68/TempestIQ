package com.tempest.tempest.subscriptions;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

public class SubscriptionsService {

    @Autowired
    SubscriptionRepository repository;    

    List<SubscriptionEntity> getAllSubscriptions(String username) throws Exception {
        return new ArrayList<SubscriptionEntity>();
    }
}
