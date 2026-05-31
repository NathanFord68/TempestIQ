package com.tempest.tempest.subscriptions;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.util.List;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(SubscriptionController.class)
class SubscriptionControllerTest {

    @Autowired
    private MockMvcTester mockMvcTester;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private SubscriptionService subscriptionService;

    @Test
    void shouldCreateSubscription() throws Exception {

        // TODO setup the when service call

        SubscriptionEntity subscriptionCreateObject = new SubscriptionEntity(
            "nathforl",
            "US", 
            "Tenessee", 
            "Kingsport", 
            70.00f, 
            80.0f
        );
        
        mockMvcTester.post()
            .uri("/subscriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(subscriptionCreateObject))
            .exchange()
            .assertThat()
            .hasStatusOk();
    }

    @Test
    void shouldReturnAllSubscriptionsAsJson() throws Exception{

        String username = "jasonborn";

        List<SubscriptionEntity> mockSubscriptions = new ArrayList<SubscriptionEntity>();

        Mockito.doReturn(mockSubscriptions).when(subscriptionService).getAllSubscriptions(username);

        mockMvcTester.get()
            .uri("/subscriptions/%s".formatted(username))
            .exchange()
            .assertThat()
            .hasStatusOk()
            .contentType()
            .isEqualTo(MediaType.APPLICATION_JSON);
    }

    @Test
    void shouldReturnAllSubscriptionsAsList(){
        mockMvcTester.get()
            .uri("/subscriptions/jasonborn")
            .exchange()
            .assertThat()
            .bodyJson()
            .extractingPath("$")
            .convertTo(list -> assertThat(list).isInstanceOf(List.class));
    }

    @Test
    void shouldReturnSubscriptionById() throws Exception {

        String username = "jasonborn";

        List<SubscriptionEntity> mockSubscriptions = new ArrayList<SubscriptionEntity>();

        Mockito.doReturn(mockSubscriptions).when(subscriptionService).getAllSubscriptions(username);

        mockSubscriptions.add(new SubscriptionEntity(
            "jasonborn", 
            "US", 
            "Tenessee", 
            "Kingsport", 
            -80.0f, 
            70.0f));

        mockSubscriptions.add(new SubscriptionEntity(
            "jasonborn", 
            "US", 
            "California", 
            "Victorville", 
            70.0f, 
            60.0f));

        mockSubscriptions.add(new SubscriptionEntity(
            "jasonborn", 
            "US", 
            "Idaho", 
            "Boise", 
            40.0f, 
            40.0f));
        SubscriptionEntity subscriptionCreateObject = new SubscriptionEntity(
            "nathforl",
            "US", 
            "Tenessee", 
            "Kingsport", 
            70.00f, 
            80.0f
        );

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

        SubscriptionEntity subscriptionCreateObject = new SubscriptionEntity(
            "nathforl",
            "US", 
            "Tenessee", 
            "Kingsport", 
            70.00f, 
            80.0f
        );

        SubscriptionEntity subscriptionUpdateObject = new SubscriptionEntity(
            "123456789",
            "nathforl",
            "US", 
            "Tenessee", 
            "Kingsport", 
            80.00f, 
            90.0f
        );
        
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