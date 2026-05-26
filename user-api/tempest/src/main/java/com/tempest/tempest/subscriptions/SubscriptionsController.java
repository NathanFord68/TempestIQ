package com.tempest.tempest.subscriptions;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subscriptions")
class resourceNameController {

    @Autowired
    SubscriptionsService service;

    @GetMapping("{username}")
    public ResponseEntity<List<SubscriptionEntity>> getAll(String username) {
        try {
            return new ResponseEntity<>(service.getAllSubscriptions(username), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}