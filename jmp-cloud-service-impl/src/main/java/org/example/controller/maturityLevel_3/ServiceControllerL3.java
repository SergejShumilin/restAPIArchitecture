package org.example.controller.maturityLevel_3;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.example.SubscriptionService;
import org.example.UserService;
import org.example.controller.ServiceController;
import org.example.dto.SubscriptionRequestDto;
import org.example.dto.SubscriptionResponseDto;
import org.example.dto.UserResponseDto;
import org.example.entity.Subscription;
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

    @GetMapping
    @ApiOperation(value = "Gets list of subscriptions", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = List.class),
            @ApiResponse(code = 204, message = "Subscriptions not found"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 500, message = "Internal server error")})
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
            response = ResponseEntity.noContent().build();
        }

        return response;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Gets subscription by id", response = Subscription.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Subscription.class),
            @ApiResponse(code = 204, message = "Subscription not found"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public ResponseEntity<SubscriptionResponseDto> getSubscription(@PathVariable Long id){
        SubscriptionResponseDto subscriptionResponseDto = subscriptionService.getSubscription(id);

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

    @PostMapping
    @ApiOperation(value = "Creates subscription", response = Subscription.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Subscription.class),
            @ApiResponse(code = 204, message = "Subscription not found"),
            @ApiResponse(code = 400, message = "Provided Subscription details is incorrect"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public ResponseEntity<?> createSubscription(@RequestBody SubscriptionRequestDto subscriptionRequestDto){

        UserResponseDto user = userService.getUser(subscriptionRequestDto.getUserId());

        if (user == null) {
            return ResponseEntity.badRequest().body("User with id = " + subscriptionRequestDto.getUserId() + " doesn't exists");
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

    @PutMapping
    @ApiOperation(value = "Updates subscription", response = Subscription.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Subscription.class),
            @ApiResponse(code = 204, message = "Subscription not found"),
            @ApiResponse(code = 400, message = "Subscription details not provided"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public ResponseEntity<?> updateSubscription(@RequestBody SubscriptionRequestDto subscriptionRequestDto) {
        if (subscriptionRequestDto.getId() == null || subscriptionRequestDto.getUserId() == null) {
            return ResponseEntity.badRequest().body("Subscription details not provided.");
        }

        UserResponseDto user = userService.getUser(subscriptionRequestDto.getUserId());

        if (user == null) {
            return ResponseEntity.badRequest().body("User with id = " + subscriptionRequestDto.getUserId() + " doesn't exists");
        }

        SubscriptionResponseDto subscriptionResponseDto = subscriptionService.updateSubscription(subscriptionRequestDto);

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

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletes subscription by id", response = Long.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Long.class),
            @ApiResponse(code = 204, message = "Subscription not found"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public ResponseEntity<?> deleteSubscription(@PathVariable Long id){

        Long subscriptionId = subscriptionService.deleteSubscription(id);

        if (subscriptionId == null){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok("Subscription with id = " + id + " was deleted");
    }
}
