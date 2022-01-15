package com.example.simpleuserrestapi.services;

import com.example.simpleuserrestapi.dto.UserDto;
import com.example.simpleuserrestapi.entities.User;

import java.util.List;

public interface UserDbService {
    User getUserByNickname(String nickname);

    User getUserById(Long id);

    List<UserDto> getAllUsers();

    User registerNewUser(User user);

    User fromDto(UserDto userDto);

    UserDto toDto(User user);

    void updateUser(UserDto userDto);

    void deleteUser(User user);

    boolean checkPassword(String nickname, String password);

    boolean userExists(String nickname);

}
