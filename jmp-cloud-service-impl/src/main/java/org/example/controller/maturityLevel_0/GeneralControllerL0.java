package org.example.controller.maturityLevel_0;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.example.SubscriptionService;
import org.example.UserService;
import org.example.dto.SubscriptionRequestDto;
import org.example.dto.SubscriptionResponseDto;
import org.example.dto.UserRequestDto;
import org.example.dto.UserResponseDto;
import org.example.entity.Action;
import org.example.entity.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-l0")
public class GeneralControllerL0 {

    @Autowired
    private UserService userService;

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping
    @ApiOperation(value = "Perform defined action")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public ResponseEntity<?> perform(@RequestBody GeneralRequest generalRequest) {

        Action action = generalRequest.getAction();

        switch (action) {
            case CREATE -> {
                return create(generalRequest);
            }
            case UPDATE -> {
                return update(generalRequest);
            }
            case DELETE -> {
                return delete(generalRequest);
            }
            case GET -> {
                return get(generalRequest);
            }
            case GET_ALL -> {
                return getAll(generalRequest);
            }
            default -> {
                return ResponseEntity.ok("Action not provided.");
            }
        }
    }

    private ResponseEntity<?> create(GeneralRequest generalRequest) {
        EntityType entityType = generalRequest.getEntityType();
        ResponseEntity<?> response;
        if (entityType.equals(EntityType.USER)) {
            UserRequestDto userRequestDto = generalRequest.getUserRequestDto();
            response = userRequestDto != null ?
                    ResponseEntity.ok(userService.createUser(userRequestDto)) :
                    ResponseEntity.ok("User body not provided");
        } else if (entityType.equals(EntityType.SUBSCRIPTION)) {
            SubscriptionRequestDto subscriptionRequestDto = generalRequest.getSubscriptionRequestDto();
            UserResponseDto user = userService.getUser(subscriptionRequestDto.getUserId());
            if (user == null) {
                response = ResponseEntity.ok(String.format("User with id = %s doesn't exists", subscriptionRequestDto.getUserId()));
            }
            response = subscriptionRequestDto != null ?
                    ResponseEntity.ok(subscriptionService.createSubscription(subscriptionRequestDto)) :
                    ResponseEntity.ok("Subscription body not provided");
        } else {
            response = ResponseEntity.ok("Body is empty");
        }

        return response;
    }

    private ResponseEntity<?> update(GeneralRequest generalRequest) {
        EntityType entityType = generalRequest.getEntityType();
        ResponseEntity<?> response;
        if (entityType.equals(EntityType.USER)) {
            UserRequestDto userRequestDto = generalRequest.getUserRequestDto();
            if (userRequestDto.getId() == null || userRequestDto.getName() == null
                    || userRequestDto.getSurname() == null || userRequestDto.getBirthday() == null) {
                response = ResponseEntity.ok("User details not provided.");
            }
            response = ResponseEntity.ok(userService.updateUser(userRequestDto));
        } else if (entityType.equals(EntityType.SUBSCRIPTION)) {
            SubscriptionRequestDto subscriptionRequestDto = generalRequest.getSubscriptionRequestDto();
            if (subscriptionRequestDto.getId() == null || subscriptionRequestDto.getUserId() == null) {
                response = ResponseEntity.badRequest().body("Subscription details not provided.");
            }
            UserResponseDto user = userService.getUser(subscriptionRequestDto.getUserId());
            response = user == null ?
                    ResponseEntity.ok(String.format("User with id = %s doesn't exists", subscriptionRequestDto.getUserId())) :
                    ResponseEntity.ok(subscriptionService.updateSubscription(subscriptionRequestDto));
        } else {
            response = ResponseEntity.ok("Body is empty");
        }

        return response;
    }

    private ResponseEntity<?> delete(GeneralRequest generalRequest) {
        EntityType entityType = generalRequest.getEntityType();
        ResponseEntity<?> response;
        if (entityType.equals(EntityType.USER)) {
            UserRequestDto userRequestDto = generalRequest.getUserRequestDto();
            Long deleteUser = userService.deleteUser(userRequestDto.getId());
            response = deleteUser != null ?
                    ResponseEntity.ok(String.format("User with id = %s was deleted", userRequestDto.getId())) :
                    ResponseEntity.ok(String.format("User with id = %s doesn't exist", userRequestDto.getId()));
        } else if (entityType.equals(EntityType.SUBSCRIPTION)) {
            SubscriptionRequestDto subscriptionRequestDto = generalRequest.getSubscriptionRequestDto();
            Long subscriptionId = subscriptionService.deleteSubscription(subscriptionRequestDto.getId());
            response = subscriptionId == null ?
                    ResponseEntity.ok(String.format("Subscription with id = %s doesn't exists", subscriptionRequestDto.getId())) :
                    ResponseEntity.ok(String.format("Subscription with id = %s was deleted", subscriptionRequestDto.getId()));
        } else {
            response = ResponseEntity.ok("Body is empty");
        }

        return response;
    }

    private ResponseEntity<?> get(GeneralRequest generalRequest) {
        EntityType entityType = generalRequest.getEntityType();
        ResponseEntity<?> response;
        if (entityType.equals(EntityType.USER)) {
            UserRequestDto userRequestDto = generalRequest.getUserRequestDto();
            UserResponseDto userResponseDto = userService.getUser(userRequestDto.getId());
            response = userResponseDto != null ?
                    ResponseEntity.ok(userResponseDto) :
                    ResponseEntity.ok(String.format("User with id = %s doesn't exist", userRequestDto.getId()));
        } else if (entityType.equals(EntityType.SUBSCRIPTION)) {
            SubscriptionRequestDto subscriptionRequestDto = generalRequest.getSubscriptionRequestDto();
            SubscriptionResponseDto subscription = subscriptionService.getSubscription(subscriptionRequestDto.getId());
            response = subscription == null ?
                    ResponseEntity.ok(String.format("Subscription with id = %s doesn't exists", subscriptionRequestDto.getId())) :
                    ResponseEntity.ok(subscription);
        } else {
            response = ResponseEntity.ok("Body is empty");
        }

        return response;
    }

    private ResponseEntity<?> getAll(GeneralRequest generalRequest) {
        EntityType entityType = generalRequest.getEntityType();
        ResponseEntity<?> response;
        if (entityType.equals(EntityType.USER)) {
            List<UserResponseDto> allUser = userService.getAllUser();
            if (allUser == null || allUser.isEmpty()) {
                response = ResponseEntity.ok("Users don't exist");
            }
            response = ResponseEntity.ok(allUser);
        } else if (entityType.equals(EntityType.SUBSCRIPTION)) {
            List<SubscriptionResponseDto> allSubscriptions = subscriptionService.getAllSubscription();
            if (allSubscriptions == null || allSubscriptions.isEmpty()) {
                response = ResponseEntity.noContent().build();
            }
            response = ResponseEntity.ok(allSubscriptions);
        } else {
            response = ResponseEntity.ok("Body is empty");
        }

        return response;
    }
}