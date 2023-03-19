package org.example.controller;

import org.example.dto.UserRequestDto;
import org.example.dto.UserResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserController {
    ResponseEntity<UserResponseDto> createUser(UserRequestDto userRequestDto);

    ResponseEntity<?> updateUser(UserRequestDto userRequestDto);

    ResponseEntity<?> deleteUser(Long id);

    ResponseEntity<UserResponseDto> getUser(Long id);

    ResponseEntity<List<UserResponseDto>> getAllUser();
}
