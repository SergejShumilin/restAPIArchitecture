package org.example.controller.maturityLevel_3;

import org.example.UserService;
import org.example.controller.UserController;
import org.example.dto.UserRequestDto;
import org.example.dto.UserResponseDto;
import org.example.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/users-l3")
public class UserControllerIL3 implements UserController {

    @Autowired
    private UserService service;

    @Override
    public ResponseEntity<List<UserResponseDto>> getAllUser( ) {
        List<UserResponseDto> allUser = service.getAllUser();

        ResponseEntity<List<UserResponseDto>> response;

        if (allUser != null && !allUser.isEmpty()) {
            for (UserResponseDto userResponseDto : allUser){
                Link selfLink = linkTo(methodOn(UserControllerIL3.class)
                        .getUser(userResponseDto.getId())).withSelfRel();
                userResponseDto.add(selfLink);
            }
            response = ResponseEntity.ok(allUser);
        } else {
            throw new UserNotFoundException("Users not found");
        }

        return response;
    }

    @Override
    public ResponseEntity<UserResponseDto>  getUser(@PathVariable Long id) {
        UserResponseDto userResponseDto = service.getUser(id);
        ResponseEntity<UserResponseDto> response;

        if (userResponseDto != null){
            Link selfLink = linkTo(methodOn(UserControllerIL3.class)
                    .getUser(userResponseDto.getId())).withSelfRel();
            userResponseDto.add(selfLink);
            response = ResponseEntity.ok(userResponseDto);
        } else {
            throw new UserNotFoundException(String.format("User with id = %s doesn't exists",
                    id));
        }

        return response;
    }

    @Override
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequestDto){
        UserResponseDto userResponseDto = service.createUser(userRequestDto);
        ResponseEntity<UserResponseDto> response;

        if (userResponseDto != null){
            Link selfLink = linkTo(methodOn(UserControllerIL3.class)
                    .getUser(userResponseDto.getId())).withSelfRel();
            userResponseDto.add(selfLink);
            response = ResponseEntity.ok(userResponseDto);
        } else {
            response = ResponseEntity.noContent().build();
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
            UserResponseDto userResponseDto = service.updateUser(userRequestDto);
            if (userResponseDto != null){
                Link selfLink = linkTo(methodOn(UserControllerIL3.class)
                        .getUser(userResponseDto.getId())).withSelfRel();
                userResponseDto.add(selfLink);
                response = ResponseEntity.ok(userResponseDto);
            } else {
                throw new UserNotFoundException(String.format("User with id = %s doesn't exists",
                        userRequestDto.getId()));
            }
        }

        return response;
    }

    @Override
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Long deleteUser = service.deleteUser(id);

        if (deleteUser == null){
            throw new UserNotFoundException(String.format("User with id = %s doesn't exists",
                    id));
        } else {
            return ResponseEntity.ok(String.format("User with id = %s was deleted", id));
        }
    }
}
