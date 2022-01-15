package com.example.simplemessengerapp.controllers;

import com.example.simplemessengerapp.dto.TokenDto;
import com.example.simplemessengerapp.dto.UserDto;
import com.example.simplemessengerapp.entities.User;
import com.example.simplemessengerapp.services.TokenAuthenticationService;
import com.example.simplemessengerapp.services.UserDbService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
public class UserController {

    //  Зависимости
    private final UserDbService userDbService;
    private final TokenAuthenticationService tokenAuthenticationService;

    //  Внедрение зависимостей через конструктор
    public UserController(UserDbService userDbService, TokenAuthenticationService tokenAuthenticationService) {
        this.userDbService = userDbService;
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    //  Обработка POST запроса в эндпоинт /user
    @PostMapping("/user")
    public ResponseEntity<TokenDto> userRegister(@RequestBody UserDto userDto) {
        User user;
        String token;
        try {
            if (userDbService.checkPassword(userDto.getName(), userDto.getPassword())) {    //  Если пользователь с таким паролем существует
                user = userDbService.getUserByUsername(userDto.getName());                  //  получаем его
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();              //  Если пароль не совпадает
            }
        } catch (NoSuchElementException e) {
            user = userDbService.registerNewUser(userDbService.fromDto(userDto));           //  Если пользователя нет - сохраняем в базе
        }
        token = tokenAuthenticationService.generateToken(user.getName());                   //  генерация токена
        return ResponseEntity.ok(new TokenDto(token));                                      //  возвращаем токен

    }

}
