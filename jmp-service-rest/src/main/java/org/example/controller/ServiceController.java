package org.example.controller;

import org.example.dto.SubscriptionRequestDto;
import org.example.dto.SubscriptionResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ServiceController {

    ResponseEntity<?> createSubscription(SubscriptionRequestDto subscriptionRequestDto);

    ResponseEntity<?> updateSubscription(SubscriptionRequestDto subscriptionRequestDto);

    ResponseEntity<?> deleteSubscription(Long id);

    ResponseEntity<SubscriptionResponseDto> getSubscription(Long id);

    ResponseEntity<List<SubscriptionResponseDto>> getAllSubscription();

}
