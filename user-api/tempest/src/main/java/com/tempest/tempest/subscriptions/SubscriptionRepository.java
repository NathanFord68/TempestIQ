package com.tempest.tempest.subscriptions;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface SubscriptionRepository extends MongoRepository<SubscriptionEntity, String>{
    
    @Query("{username: '?0")
    List<SubscriptionEntity> findAllByUsername(String username);

}
