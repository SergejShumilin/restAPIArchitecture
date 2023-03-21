package org.example;

import org.example.dto.UserRequestDto;
import org.example.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto createUser(UserRequestDto userRequestDto);

    UserResponseDto updateUser(UserRequestDto userRequestDto);

    Long deleteUser(Long id);

    UserResponseDto getUser(Long id);

    List<UserResponseDto> getAllUser();

}
