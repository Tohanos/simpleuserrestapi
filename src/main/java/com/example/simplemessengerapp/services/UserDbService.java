package com.example.simplemessengerapp.services;

import com.example.simplemessengerapp.dto.UserDto;
import com.example.simplemessengerapp.entities.User;

public interface UserDbService {
    User getUserByUsername(String name);

    User getUserById(Long id);

    User registerNewUser(User user);

    User fromDto(UserDto userDto);

    UserDto toDto(User user);

    void deleteUser(User user);

    boolean checkPassword(String name, String password);

}
