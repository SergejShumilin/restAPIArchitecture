package org.example.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.example.dto.UserRequestDto;
import org.example.dto.UserResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

public interface UserController {

    @PostMapping
    @ApiOperation(value = "Creates user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "User not found"),
            @ApiResponse(code = 400, message = "Provided user details is incorrect"),
            @ApiResponse(code = 500, message = "Internal server error")})
    ResponseEntity<UserResponseDto> createUser(UserRequestDto userRequestDto);

    @PutMapping
    @ApiOperation(value = "Updates user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "User not found"),
            @ApiResponse(code = 400, message = "User details not provided"),
            @ApiResponse(code = 500, message = "Internal server error")})
    ResponseEntity<?> updateUser(UserRequestDto userRequestDto);

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletes user by id", response = Long.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Long.class),
            @ApiResponse(code = 204, message = "User not found"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 500, message = "Internal server error")})
    ResponseEntity<?> deleteUser(Long id);

    @GetMapping("/{id}")
    @ApiOperation(value = "Gets user by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "User not found"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 500, message = "Internal server error")})
    ResponseEntity<UserResponseDto> getUser(Long id);

    @GetMapping
    @ApiOperation(value = "Gets list of users", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = List.class),
            @ApiResponse(code = 204, message = "Users not found"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 500, message = "Internal server error")})
    ResponseEntity<List<UserResponseDto>> getAllUser();
}
