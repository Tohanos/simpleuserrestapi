package com.example.simpleuserrestapi.repository;

import com.example.simpleuserrestapi.entities.User;
import com.example.simpleuserrestapi.repositories.UserRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private final String NICKNAME = "Kolya";
    private final String NAME = "Nikolay";
    private final String PASS = "12345";

    @Test
    public void findAllUsers() {
        List<User> users = userRepository.findAll();

        int nOfUsers = 2;
        assertThat(users).hasSize(nOfUsers);
    }


    @Test
    public void testUsers() {
        if (userRepository.getUserByNickname(NICKNAME).isEmpty()) {
            User user = new User();
            user.setNickname(NICKNAME);
            user.setName(NAME);
            user.setPassword(PASS);

            saveUser(user);
        }
        User newUser = userRepository.getUserByNickname(NICKNAME).get();
        assertThat(NICKNAME).isEqualTo(newUser.getNickname());
        assertThat(NAME).isEqualTo(newUser.getName());
        assertThat(PASS).isEqualTo(newUser.getPassword());
        Optional<User> user = userRepository.getUserByNickname(NICKNAME);
        user.ifPresent(this::deleteUser);

    }

    @Transactional
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

}
