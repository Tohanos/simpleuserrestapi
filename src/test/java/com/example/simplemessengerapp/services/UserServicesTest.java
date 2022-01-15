package com.example.simplemessengerapp.services;

import com.example.simplemessengerapp.dto.UserDto;
import com.example.simplemessengerapp.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserServicesTest {

    private final String NAME = "Nikolay";
    private final String PASS = "12345";
    private final String ERROR_MESSAGE = "User not found";

    @Autowired
    private UserDbService service;

    @Test
    public void createDeleteUserTest() {
        User user = new User();
        user.setName(NAME);
        user.setPassword(PASS);

        service.registerNewUser(user);

        user = service.getUserByUsername(NAME);
        assertThat(user.getName()).isEqualTo(NAME);
        assertThat(user.getPassword()).isEqualTo(PASS);
        assertThat(service.checkPassword(NAME, PASS)).isTrue();

        service.deleteUser(user);

        try {
            service.getUserByUsername(NAME);
        } catch (Exception e) {
            assertThat(e).hasMessage(ERROR_MESSAGE);
        }
    }

    @Test
    public void fromToDto() {
        User user = new User();
        user.setName(NAME);
        user.setPassword(PASS);

        UserDto dto = service.toDto(user);

        assertThat(service.fromDto(dto).getName()).isEqualTo(NAME);
        assertThat(service.fromDto(dto).getPassword()).isEqualTo(PASS);

    }




}
