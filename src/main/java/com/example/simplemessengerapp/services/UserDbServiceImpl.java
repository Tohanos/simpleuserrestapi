package com.example.simplemessengerapp.services;

import com.example.simplemessengerapp.dto.UserDto;
import com.example.simplemessengerapp.entities.User;
import com.example.simplemessengerapp.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

//  Сервис для работы с пользователями в бд
@Service
public class UserDbServiceImpl implements UserDbService {

    private final UserRepository repository;

    private final String ERROR_MESSAGE = "User not found";

    public UserDbServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public User getUserByUsername(String name) throws NoSuchElementException{
        Optional<User> userOptional = repository.getUserByName(name);
        if (userOptional.isEmpty()) {
            throw new NoSuchElementException(ERROR_MESSAGE);
        }
        return userOptional.get();
    }

    @Override
    @Transactional
    public User getUserById(Long id) {
        return repository.getById(id);
    }


    @Override
    @Transactional
    public User registerNewUser(User user) {
        if (repository.getUserByName(user.getName()).isPresent())
            return repository.getUserByName(user.getName()).get();
        return repository.save(user);
    }

    @Override
    public User fromDto(UserDto userDto) {
        if (repository.getUserByName(userDto.getName()).isEmpty()) {
            User user = new User();
            user.setName(userDto.getName());
            user.setPassword(userDto.getPassword());
            return user;
        }
        return repository.getUserByName(userDto.getName()).get();
    }

    @Override
    public UserDto toDto(User user) {
        return new UserDto(user.getName(), user.getPassword());
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        if (repository.getUserByName(user.getName()).isPresent()) {
            repository.delete(user);
        }
    }

    @Override
    public boolean checkPassword(String name, String password) throws NoSuchElementException{
        return getUserByUsername(name).getPassword().equals(password);
    }

}
