package com.example.simplemessengerapp.repository;

import com.example.simplemessengerapp.entities.User;
import com.example.simplemessengerapp.repositories.UserRepository;

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
        if (userRepository.getUserByName(NAME).isEmpty()) {
            User user = new User();
            user.setName(NAME);
            user.setPassword(PASS);

            saveUser(user);
        }
        User newUser = userRepository.getUserByName(NAME).get();
        assertThat(NAME).isEqualTo(newUser.getName());
        assertThat(PASS).isEqualTo(newUser.getPassword());
        Optional<User> user = userRepository.getUserByName(NAME);
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
