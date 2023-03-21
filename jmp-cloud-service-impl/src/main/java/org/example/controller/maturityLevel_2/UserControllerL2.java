package org.example.controller.maturityLevel_2;

import org.example.UserService;
import org.example.controller.UserController;
import org.example.dto.UserRequestDto;
import org.example.dto.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserControllerL2 implements UserController {

    @Autowired
    private UserService service;

    @Override
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequestDto){
        UserResponseDto user = service.createUser(userRequestDto);
        ResponseEntity<UserResponseDto> response;

        if (user == null){
            response = ResponseEntity.badRequest().build();
        } else {
            response = ResponseEntity.ok(user);
        }
        return response;
    }

    @Override
    public ResponseEntity<?> updateUser(@RequestBody UserRequestDto userRequestDto) {
        ResponseEntity<?> response;
        if (userRequestDto.getId() == null || userRequestDto.getName() == null
            || userRequestDto.getSurname() == null || userRequestDto.getBirthday() == null) {
            response = ResponseEntity.badRequest().body("User details not provided.");
        } else {
            response = ResponseEntity.ok(service.updateUser(userRequestDto));
        }
        return response;
    }

    @Override
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Long deleteUser = service.deleteUser(id);
        ResponseEntity<?> response;
        if (deleteUser == null){
            response = ResponseEntity.noContent().build();
        } else {
            response = ResponseEntity.ok(String.format("User with id = %s was deleted", id));
        }
        return response;
    }

    @Override
    public ResponseEntity<UserResponseDto>  getUser(@PathVariable Long id) {
        UserResponseDto user = service.getUser(id);
        ResponseEntity<UserResponseDto> response;
        if (user == null){
            response = ResponseEntity.noContent().build();
        } else {
            response = ResponseEntity.ok(user);
        }
        return response;
    }

    @Override
    public ResponseEntity<List<UserResponseDto>> getAllUser( ) {
        List<UserResponseDto> allUser = service.getAllUser();
        ResponseEntity<List<UserResponseDto>> response;
        if (allUser == null || allUser.isEmpty()){
            response = ResponseEntity.noContent().build();
        } else {
            response = ResponseEntity.ok(allUser);
        }
        return response;
    }
}
