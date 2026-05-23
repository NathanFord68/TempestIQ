package com.tempest.tempest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import com.tempest.tempest.Entities.Subscription;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class SubscriptionControllerTest {

    @Autowired
    private MockMvcTester mockMvcTester;

    @Autowired
    private ObjectMapper objectMapper;

    Subscription subscriptionCreateObject = new Subscription(
        "nathforl",
        "US", 
        "Tenessee", 
        "Kingsport", 
        70.00f, 
        80.0f
    );

    Subscription subscriptionUpdateObject = new Subscription(
        "123456789",
        "nathforl",
        "US", 
        "Tenessee", 
        "Kingsport", 
        80.00f, 
        90.0f
    );

    
    @Test
    void shouldCreateSubscription() throws Exception {
        mockMvcTester.post()
            .uri("/subscriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(subscriptionCreateObject))
            .exchange()
            .assertThat()
            .hasStatusOk();
    }

    @Test
    void shouldReturnAllSubscriptionsAsJson() {
        mockMvcTester.get()
            .uri("/subscriptions")
            .exchange()
            .assertThat()
            .hasStatusOk()
            .contentType()
            .isEqualTo(MediaType.APPLICATION_JSON);
    }

    @Test
    void shouldReturnAllSubscriptionsAsList(){
        mockMvcTester.get()
            .uri("/subscriptions")
            .exchange()
            .assertThat()
            .bodyJson()
            .extractingPath("$")
            .convertTo(list -> assertThat(list).isInstanceOf(List.class));
    }

    @Test
    void shouldReturnSubscriptionById() throws Exception {

        var createResponse = mockMvcTester.post()
            .uri("/subscriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(subscriptionCreateObject))
            .exchange();

        String id = createResponse.assertThat()
            .bodyJson()
            .extractingPath("$.id")
            .asString()
            .actual();

        mockMvcTester.get()
            .uri("/subscriptions/{id}", id)
            .exchange()
            .assertThat()
            .hasStatusOk()
            .bodyJson()
            .extractingPath("$.id")
            .asString()
            .isEqualTo("123456789");
    }

    @Test
    void shouldUpdateSubscription() throws Exception {

        var createResponse = mockMvcTester.post()
            .uri("/subscriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(subscriptionCreateObject))
            .exchange();

        String id = createResponse.assertThat()
            .bodyJson()
            .extractingPath("$.id")
            .asString()
            .actual();

        mockMvcTester.put()
            .uri("/subscriptions/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(subscriptionUpdateObject))
            .exchange()
            .assertThat()
            .hasStatusOk()
            .bodyJson()
            .extractingPath("$.locationName")
            .asString()
            .isEqualTo("Knoxville");
    }

    @Test
    void shouldDeleteSubscription() throws Exception {

        var createResponse = mockMvcTester.post()
            .uri("/subscriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content("123456789")
            .exchange();

        String id = createResponse.assertThat()
            .bodyJson()
            .extractingPath("$.id")
            .asString()
            .actual();

        mockMvcTester.delete()
            .uri("/subscriptions/{id}", id)
            .exchange()
            .assertThat()
            .hasStatusOk();

        mockMvcTester.get()
            .uri("/subscriptions/{id}", id)
            .exchange()
            .assertThat()
            .hasStatus(HttpStatus.NOT_FOUND);
    }
}