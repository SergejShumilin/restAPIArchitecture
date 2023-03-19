package org.example.controller.maturityLevel_1;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.example.UserService;
import org.example.controller.maturityLevel_1.requests.UserRequest;
import org.example.dto.UserRequestDto;
import org.example.dto.UserResponseDto;
import org.example.entity.Action;
import org.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users-l1")
public class UserControllerL1 {

    @Autowired
    private UserService userService;


    @PostMapping
    @ApiOperation(value = "Perform defined action with user", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = User.class),
            @ApiResponse(code = 500, message = "Internal server error")})
    public ResponseEntity<?> perform(@RequestBody UserRequest userRequest) {

        Action action = userRequest.getAction();
        UserRequestDto userRequestDto = userRequest.getRequestDto();

        switch (action) {
            case CREATE -> {
                return userRequestDto != null ?
                        ResponseEntity.ok(userService.createUser(userRequestDto)) :
                        ResponseEntity.ok("User body not provided");
            }
            case UPDATE -> {
                if (userRequestDto.getId() == null || userRequestDto.getName() == null
                        || userRequestDto.getSurname() == null || userRequestDto.getBirthday() == null) {
                    return ResponseEntity.ok("User details not provided.");
                }
                return ResponseEntity.ok(userService.updateUser(userRequestDto));
            }
            case DELETE -> {
                Long deleteUser = userService.deleteUser(userRequestDto.getId());
                return deleteUser != null ?
                        ResponseEntity.ok(String.format("User with id = %s was deleted", userRequestDto.getId())) :
                        ResponseEntity.ok(String.format("User with id = %s doesn't exist", userRequestDto.getId()));
            }
            case GET -> {
                UserResponseDto userResponseDto = userService.getUser(userRequestDto.getId());
                return userResponseDto != null ?
                        ResponseEntity.ok(userResponseDto) :
                        ResponseEntity.ok(String.format("User with id = %s doesn't exist", userRequestDto.getId()));
            }
            case GET_ALL -> {
                List<UserResponseDto> allUser = userService.getAllUser();
                if (allUser == null || allUser.isEmpty()) {
                    return ResponseEntity.ok("Users don't exist");
                }
                return ResponseEntity.ok(allUser);
            }
            default -> {
                return ResponseEntity.ok("Action not provided.");
            }
        }
    }
}
