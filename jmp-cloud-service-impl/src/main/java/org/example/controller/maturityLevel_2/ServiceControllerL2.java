package org.example.controller.maturityLevel_2;

import org.example.SubscriptionService;
import org.example.UserService;
import org.example.controller.ServiceController;
import org.example.dto.SubscriptionRequestDto;
import org.example.dto.SubscriptionResponseDto;
import org.example.dto.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
public class ServiceControllerL2 implements ServiceController {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<?> createSubscription(@RequestBody SubscriptionRequestDto subscriptionRequestDto){

        UserResponseDto user = userService.getUser(subscriptionRequestDto.getUserId());
        ResponseEntity<?> response;
        if (user == null) {
            return ResponseEntity.badRequest()
                    .body(String.format("User with id = %s doesn't exists", subscriptionRequestDto.getUserId()));
        }

        SubscriptionResponseDto subscription = subscriptionService.createSubscription(subscriptionRequestDto);

        if (subscription == null){
            response = ResponseEntity.badRequest().build();
        } else {
            response = ResponseEntity.ok(subscription);
        }
        return response;
    }

    @Override
    public ResponseEntity<?> updateSubscription(@RequestBody SubscriptionRequestDto subscriptionRequestDto) {
        if (subscriptionRequestDto.getId() == null || subscriptionRequestDto.getUserId() == null) {
            return ResponseEntity.badRequest().body("Subscription details not provided.");
        }

        UserResponseDto user = userService.getUser(subscriptionRequestDto.getUserId());

        if (user == null) {
            return ResponseEntity.badRequest()
                    .body(String.format("User with id = %s doesn't exists", subscriptionRequestDto.getUserId()));
        }

        return ResponseEntity.ok(subscriptionService.updateSubscription(subscriptionRequestDto));

    }

    @Override
    public ResponseEntity<?> deleteSubscription(@PathVariable Long id){

        Long subscriptionId = subscriptionService.deleteSubscription(id);

        if (subscriptionId == null){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(String.format("Subscription with id = %s was deleted", id));
    }

    @Override
    public ResponseEntity<SubscriptionResponseDto> getSubscription(@PathVariable Long id){
        SubscriptionResponseDto subscription = subscriptionService.getSubscription(id);

        if (subscription == null){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(subscription);
    }

    @Override
    public ResponseEntity<List<SubscriptionResponseDto>> getAllSubscription(){
        List<SubscriptionResponseDto> allSubscriptions = subscriptionService.getAllSubscription();

        if (allSubscriptions == null || allSubscriptions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(allSubscriptions);
    }
}
