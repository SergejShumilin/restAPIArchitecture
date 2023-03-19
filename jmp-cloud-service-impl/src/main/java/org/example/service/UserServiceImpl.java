package org.example.service;

import org.example.UserService;
import org.example.dao.UserRepository;
import org.example.dto.UserRequestDto;
import org.example.dto.UserResponseDto;
import org.example.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User requestUser = mapper.map(userRequestDto, User.class);
        requestUser.setBirthday(LocalDate.parse(userRequestDto.getBirthday()));
        User user = repository.save(requestUser);

        return mapper.map(user, UserResponseDto.class);
    }

    @Override
    public UserResponseDto updateUser(UserRequestDto userRequestDto) {
        Optional<User> optionalUser = repository.findById(userRequestDto.getId());
        User user = null;
        if (optionalUser.isPresent()){
            User requestUser = mapper.map(userRequestDto, User.class);
            requestUser.setBirthday(LocalDate.parse(userRequestDto.getBirthday()));
            user = repository.save(requestUser);
        } else {
            throw new NoSuchElementException();
        }

        return mapper.map(user, UserResponseDto.class);
    }

    @Override
    public Long deleteUser(Long id) {
        Optional<User> optionalUser = repository.findById(id);
        if (optionalUser.isEmpty()) {
            return null;
        } else {
            repository.deleteById(id);
        }

        return id;
    }

    @Override
    public UserResponseDto getUser(Long id) {
        Optional<User> optionalUser = repository.findById(id);

        return optionalUser.map(user -> mapper.map(optionalUser.get(), UserResponseDto.class))
                .orElse(null);
    }

    @Override
    public List<UserResponseDto> getAllUser() {
        Iterable<User> allUser = repository.findAll();

        return StreamSupport.stream(allUser.spliterator(), false)
                .map(user -> mapper.map(user, UserResponseDto.class))
                .collect(Collectors.toList());
    }
}
