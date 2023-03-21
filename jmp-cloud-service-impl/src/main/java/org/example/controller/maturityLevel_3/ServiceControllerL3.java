package org.example.controller.maturityLevel_3;

import org.example.SubscriptionService;
import org.example.UserService;
import org.example.controller.ServiceController;
import org.example.dto.SubscriptionRequestDto;
import org.example.dto.SubscriptionResponseDto;
import org.example.dto.UserResponseDto;
import org.example.exception.SubscriptionNotFoundException;
import org.example.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/subscriptions-l3")
public class ServiceControllerL3 implements ServiceController {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<List<SubscriptionResponseDto>> getAllSubscription(){
        List<SubscriptionResponseDto> allSubscriptions = subscriptionService.getAllSubscription();

        ResponseEntity<List<SubscriptionResponseDto>> response;

        if (allSubscriptions != null && !allSubscriptions.isEmpty()) {
            for (SubscriptionResponseDto subscriptionResponseDto : allSubscriptions){
                Link selfLink = linkTo(methodOn(ServiceControllerL3.class)
                        .getSubscription(subscriptionResponseDto.getId())).withSelfRel();
                subscriptionResponseDto.add(selfLink);

                Link userLink = linkTo(methodOn(UserControllerIL3.class)
                        .getUser(subscriptionResponseDto.getUserId())).withRel("user");
                subscriptionResponseDto.add(userLink);
            }
            response = ResponseEntity.ok(allSubscriptions);
        } else {
            throw new SubscriptionNotFoundException("Subscriptions not found");
        }

        return response;
    }

    @Override
    public ResponseEntity<SubscriptionResponseDto> getSubscription(@PathVariable Long id){
        SubscriptionResponseDto subscriptionResponseDto = subscriptionService.getSubscription(id);

        ResponseEntity<SubscriptionResponseDto> response;

        if (subscriptionResponseDto != null){
            Link selfLink = linkTo(methodOn(UserControllerIL3.class)
                    .getUser(subscriptionResponseDto.getId())).withSelfRel();
            subscriptionResponseDto.add(selfLink);
            response = ResponseEntity.ok(subscriptionResponseDto);
        } else {
            throw new SubscriptionNotFoundException(String.format("Subscription with id = %s doesn't exists",
                    id));
        }

        return response;
    }

    @Override
    public ResponseEntity<?> createSubscription(@RequestBody SubscriptionRequestDto subscriptionRequestDto){

        UserResponseDto user = userService.getUser(subscriptionRequestDto.getUserId());

        if (user == null) {
            throw new UserNotFoundException(String.format("User with id = %s doesn't exists",
                    subscriptionRequestDto.getUserId()));
        }

        SubscriptionResponseDto subscriptionResponseDto = subscriptionService.createSubscription(subscriptionRequestDto);

        ResponseEntity<SubscriptionResponseDto> response;

        if (subscriptionResponseDto != null){
            Link selfLink = linkTo(methodOn(UserControllerIL3.class)
                    .getUser(subscriptionResponseDto.getId())).withSelfRel();
            subscriptionResponseDto.add(selfLink);
            response = ResponseEntity.ok(subscriptionResponseDto);
        } else {
            response = ResponseEntity.noContent().build();
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
            throw new UserNotFoundException(String.format("User with id = %s doesn't exists",
                    subscriptionRequestDto.getUserId()));
        }

        SubscriptionResponseDto subscriptionResponseDto = subscriptionService.updateSubscription(subscriptionRequestDto);

        ResponseEntity<SubscriptionResponseDto> response;

        if (subscriptionResponseDto != null){
            Link selfLink = linkTo(methodOn(UserControllerIL3.class)
                    .getUser(subscriptionResponseDto.getId())).withSelfRel();
            subscriptionResponseDto.add(selfLink);
            response = ResponseEntity.ok(subscriptionResponseDto);
        } else {
            throw new SubscriptionNotFoundException(String.format("Subscription with id = %s doesn't exists",
                    subscriptionRequestDto.getId()));
        }

        return response;

    }

    @Override
    public ResponseEntity<?> deleteSubscription(@PathVariable Long id){

        Long subscriptionId = subscriptionService.deleteSubscription(id);

        if (subscriptionId == null){
            throw new SubscriptionNotFoundException(String.format("Subscription with id = %s doesn't exists",
                    id));
        }

        return ResponseEntity.ok(String.format("Subscription with id = %s was deleted", id));
    }
}
