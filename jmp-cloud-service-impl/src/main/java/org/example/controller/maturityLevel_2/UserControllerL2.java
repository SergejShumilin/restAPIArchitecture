package org.example.controller.maturityLevel_2;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.example.UserService;
import org.example.controller.UserController;
import org.example.dto.UserRequestDto;
import org.example.dto.UserResponseDto;
import org.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserControllerL2 implements UserController {

    @Autowired
    private UserService service;

    @PostMapping
    @ApiOperation(value = "Creates user", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = User.class),
            @ApiResponse(code = 204, message = "User not found"),
            @ApiResponse(code = 400, message = "Provided user details is incorrect"),
            @ApiResponse(code = 500, message = "Internal server error")})
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

    @PutMapping
    @ApiOperation(value = "Updates user", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = User.class),
            @ApiResponse(code = 204, message = "User not found"),
            @ApiResponse(code = 400, message = "User details not provided"),
            @ApiResponse(code = 500, message = "Internal server error")})
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

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletes user by id", response = Long.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Long.class),
            @ApiResponse(code = 204, message = "User not found"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 500, message = "Internal server error")})
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

    @GetMapping("/{id}")
    @ApiOperation(value = "Gets user by id", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = User.class),
            @ApiResponse(code = 204, message = "User not found"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 500, message = "Internal server error")})
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

    @GetMapping
    @ApiOperation(value = "Gets list of users", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = List.class),
            @ApiResponse(code = 204, message = "Users not found"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 500, message = "Internal server error")})
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
