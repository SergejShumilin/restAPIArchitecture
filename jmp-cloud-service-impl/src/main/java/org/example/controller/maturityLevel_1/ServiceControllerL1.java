package org.example.controller.maturityLevel_1;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.example.SubscriptionService;
import org.example.UserService;
import org.example.controller.maturityLevel_1.requests.SubscriptionRequest;
import org.example.dto.SubscriptionRequestDto;
import org.example.dto.SubscriptionResponseDto;
import org.example.dto.UserResponseDto;
import org.example.entity.Action;
import org.example.entity.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/subscriptions-l1")
public class ServiceControllerL1 {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UserService userService;

    @PostMapping
    @ApiOperation(value = "Perform defined action with subscription", response = Subscription.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Subscription.class),
            @ApiResponse(code = 500, message = "Internal server error")})
    public ResponseEntity<?> perform(@RequestBody SubscriptionRequest subscriptionRequest){
        Action action = subscriptionRequest.getAction();
        SubscriptionRequestDto subscriptionRequestDto = subscriptionRequest.getRequestDto();

        switch (action){
            case CREATE -> {
                UserResponseDto user = userService.getUser(subscriptionRequestDto.getUserId());
                if (user == null) {
                    return ResponseEntity.ok(String.format("User with id = %s doesn't exists", subscriptionRequestDto.getUserId()));
                }
                return subscriptionRequestDto != null ?
                        ResponseEntity.ok(subscriptionService.createSubscription(subscriptionRequestDto)) :
                        ResponseEntity.ok("Subscription body not provided");
            }
            case UPDATE -> {
                if (subscriptionRequestDto.getId() == null || subscriptionRequestDto.getUserId() == null) {
                    return ResponseEntity.badRequest().body("Subscription details not provided.");
                }
                UserResponseDto user = userService.getUser(subscriptionRequestDto.getUserId());
                return user == null ?
                        ResponseEntity.ok(String.format("User with id = %s doesn't exists", subscriptionRequestDto.getUserId())) :
                        ResponseEntity.ok(subscriptionService.updateSubscription(subscriptionRequestDto));
            }
            case DELETE -> {
                Long subscriptionId = subscriptionService.deleteSubscription(subscriptionRequestDto.getId());
                return subscriptionId == null ?
                        ResponseEntity.ok(String.format("Subscription with id = %s doesn't exists", subscriptionRequestDto.getId())) :
                        ResponseEntity.ok(String.format("Subscription with id = %s was deleted", subscriptionRequestDto.getId()));
            }
            case GET -> {
                SubscriptionResponseDto subscription = subscriptionService.getSubscription(subscriptionRequestDto.getId());
                return subscription == null ?
                        ResponseEntity.ok(String.format("Subscription with id = %s doesn't exists", subscriptionRequestDto.getId())) :
                        ResponseEntity.ok(subscription);
            }
            case GET_ALL -> {
                List<SubscriptionResponseDto> allSubscriptions = subscriptionService.getAllSubscription();
                if (allSubscriptions == null || allSubscriptions.isEmpty()) {
                    return ResponseEntity.noContent().build();
                }
                return ResponseEntity.ok(allSubscriptions);
            }
            default -> { return ResponseEntity.ok("Action not provided."); }
        }
    }
}
