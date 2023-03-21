package org.example.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.example.dto.SubscriptionRequestDto;
import org.example.dto.SubscriptionResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

public interface ServiceController {

    @PostMapping
    @ApiOperation(value = "Creates subscription")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "Subscription not found"),
            @ApiResponse(code = 400, message = "Provided Subscription details is incorrect"),
            @ApiResponse(code = 500, message = "Internal server error")})
    ResponseEntity<?> createSubscription(SubscriptionRequestDto subscriptionRequestDto);

    @PutMapping
    @ApiOperation(value = "Updates subscription")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "Subscription not found"),
            @ApiResponse(code = 400, message = "Subscription details not provided"),
            @ApiResponse(code = 500, message = "Internal server error")})
    ResponseEntity<?> updateSubscription(SubscriptionRequestDto subscriptionRequestDto);

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletes subscription by id", response = Long.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Long.class),
            @ApiResponse(code = 204, message = "Subscription not found"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 500, message = "Internal server error")})
    ResponseEntity<?> deleteSubscription(Long id);

    @GetMapping("/{id}")
    @ApiOperation(value = "Gets subscription by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "Subscription not found"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 500, message = "Internal server error")})
    ResponseEntity<SubscriptionResponseDto> getSubscription(Long id);

    @GetMapping
    @ApiOperation(value = "Gets list of subscriptions", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = List.class),
            @ApiResponse(code = 204, message = "Subscriptions not found"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 500, message = "Internal server error")})
    ResponseEntity<List<SubscriptionResponseDto>> getAllSubscription();

}
