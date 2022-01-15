package com.example.simpleuserrestapi.services;

import com.example.simpleuserrestapi.dto.UserDto;
import com.example.simpleuserrestapi.entities.User;
import com.example.simpleuserrestapi.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public User getUserByNickname(String nickname) throws NoSuchElementException{
        Optional<User> userOptional = repository.getUserByNickname(nickname);
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
    public List<UserDto> getAllUsers() {
        return repository.findAll()
                .stream()
                .map(user -> toDto(user))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public User registerNewUser(User user) {
        if (repository.getUserByName(user.getNickname()).isPresent())
            return repository.getUserByName(user.getNickname()).get();
        return repository.save(user);
    }

    @Override
    public User fromDto(UserDto userDto) {
        User user;
        if (repository.getUserByNickname(userDto.getNickname()).isEmpty()) {
            user = new User();
            user.setNickname(userDto.getNickname());
            user.setName(userDto.getName());
            user.setSurname1(userDto.getSurname1());
            user.setSurname2(userDto.getSurname2());
            user.setBirthday(Date.valueOf(userDto.getBirthday() == null ? "1970-01-01" : userDto.getBirthday()));
            user.setEmail(userDto.getEmail());
            user.setPhone(userDto.getPhone());
            user.setPassword(userDto.getPassword());
            return user;
        }
        user = repository.getUserByNickname(userDto.getNickname()).get();
        if (userDto.getName() != null) user.setName(userDto.getName());
        if (userDto.getSurname1() != null) user.setSurname1(userDto.getSurname1());
        if (userDto.getSurname2() != null) user.setSurname2(userDto.getSurname2());
        if (userDto.getBirthday() != null) user.setBirthday(Date.valueOf(userDto.getBirthday()));
        if (userDto.getEmail() != null) user.setEmail(userDto.getEmail());
        if (userDto.getPhone() != null) user.setPhone(userDto.getPhone());
        if (userDto.getPassword() != null) user.setPassword(userDto.getPassword());
        return user;
    }

    @Override
    public UserDto toDto(User user) {
        return new UserDto(user.getNickname(),
                user.getName(),
                user.getSurname1(),
                user.getSurname2(),
                user.getBirthday().toString(),
                user.getEmail(),
                user.getPhone(),
                user.getPassword());
    }

    @Override
    @Transactional
    public void updateUser(UserDto userDto) {
        User user = fromDto(userDto);
        repository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        if (repository.getUserByNickname(user.getNickname()).isPresent()) {
            repository.delete(user);
        }
    }

    @Override
    public boolean userExists(String nickname) {
        return repository.getUserByNickname(nickname).isPresent();
    }

    @Override
    public boolean checkPassword(String nickname, String password) throws NoSuchElementException{
        return getUserByNickname(nickname).getPassword().equals(password);
    }

}
