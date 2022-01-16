package com.example.simpleuserrestapi.services;

import com.example.simpleuserrestapi.dto.UserDto;
import com.example.simpleuserrestapi.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserServicesTest {

    private final String NAME = "Nikolay";
    private final String NICKNAME = "Kolya";
    private final String PASS = "12345";
    private final String ERROR_MESSAGE = "User not found";

    @Autowired
    private UserDbService service;

    @Test
    public void createDeleteUserTest() {
        User user = new User();
        user.setNickname(NICKNAME);
        user.setName(NAME);
        user.setPassword(PASS);

        service.registerNewUser(user);

        user = service.getUserByNickname(NICKNAME);
        assertThat(user.getNickname()).isEqualTo(NICKNAME);
        assertThat(user.getName()).isEqualTo(NAME);
        assertThat(user.getPassword()).isEqualTo(PASS);
        assertThat(service.checkPassword(NICKNAME, PASS)).isTrue();

        service.deleteUser(user);

        try {
            service.getUserByNickname(NICKNAME);
        } catch (Exception e) {
            assertThat(e).hasMessage(ERROR_MESSAGE);
        }
    }

    @Test
    public void fromToDto() {
        User user = new User();
        user.setNickname(NICKNAME);
        user.setName(NAME);
        user.setPassword(PASS);

        UserDto dto = service.toDto(user);

        assertThat(service.fromDto(dto).getNickname()).isEqualTo(NICKNAME);
        assertThat(service.fromDto(dto).getName()).isEqualTo(NAME);
        assertThat(service.fromDto(dto).getPassword()).isEqualTo(PASS);

    }




}
