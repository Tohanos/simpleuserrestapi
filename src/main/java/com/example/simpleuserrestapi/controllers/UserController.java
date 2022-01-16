package com.example.simpleuserrestapi.controllers;

import com.example.simpleuserrestapi.dto.TokenDto;
import com.example.simpleuserrestapi.dto.UserDto;
import com.example.simpleuserrestapi.entities.User;
import com.example.simpleuserrestapi.services.TokenAuthenticationService;
import com.example.simpleuserrestapi.services.UserDbService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class UserController {

    //  Зависимости
    private final UserDbService userDbService;
    private final TokenAuthenticationService tokenAuthenticationService;

    //  Константы
    private final static String BEARER = "Bearer";

    //  Внедрение зависимостей через конструктор
    public UserController(UserDbService userDbService, TokenAuthenticationService tokenAuthenticationService) {
        this.userDbService = userDbService;
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    //  Обработка POST запроса в эндпоинт /login - вход пользователя и выдача токена
    @PostMapping("/login")
    public ResponseEntity<TokenDto> userRegister(@RequestBody UserDto userDto) {
        User user;
        String token;
        try {
            if (userDbService.checkPassword(userDto.getNickname(), userDto.getPassword())) {    //  Если пользователь с таким паролем существует
                user = userDbService.getUserByNickname(userDto.getNickname());                  //  получаем его
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();              //  Если пароль не совпадает
            }
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        token = tokenAuthenticationService.generateToken(user.getNickname());                   //  генерация токена
        return ResponseEntity.ok(new TokenDto(token));                                      //  возвращаем токен

    }

    // Обработка POST запроса в эндпоинт /new на создание нового пользователя
    @PostMapping("/new")
    public ResponseEntity<TokenDto> createNewUser(@RequestBody UserDto userDto) {
        User user;
        String token;
        if (userDto.getNickname() != null) {
            if (!userDbService.userExists(userDto.getNickname())) {
                user = userDbService.registerNewUser(userDbService.fromDto(userDto));           //  Если пользователя нет - создаём
                token = tokenAuthenticationService.generateToken(user.getNickname());           //  Генерируем токен
                return ResponseEntity.ok(new TokenDto(token));                                  //  возвращаем токен
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //  Обработка GET запроса в эндпоинт /users на выдачу списка пользователей
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers(@RequestHeader(BEARER) String auth) {
        if (tokenAuthenticationService.tokenExist(auth))
            return ResponseEntity.ok(userDbService.getAllUsers());
        return ResponseEntity.status(401).build();    //  авторизация не прошла
    }

    //  Обработка POST запроса в эндпоинт /update для изменения свойств пользователя
    @PostMapping("/update")
    public ResponseEntity<String> updateUser(@RequestHeader(BEARER) String auth, @RequestBody UserDto userDto) {
        if (tokenAuthenticationService.tokenExist(auth)) {                          //  авторизуемся
            String nickname = tokenAuthenticationService.extractFromToken(auth);    //  извлекаем ник из токена
            userDto.setNickname(nickname);                                          //  устанавливаем его принудительно, чтобы пользователь не мог его поменять
            userDbService.updateUser(userDto);                                      //  обновляем данные пользователя
            return ResponseEntity.ok("Accepted");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();              //  Если пароль не совпадает - возвращаем ошибку
    }

    //  Обработка GET запроса в эндпоинт /delete для удаления пользователя
    @GetMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestHeader(BEARER) String auth) {
        if (tokenAuthenticationService.tokenExist(auth)) {
            String nickname = tokenAuthenticationService.extractFromToken(auth);
            User user = userDbService.getUserByNickname(nickname);
            userDbService.deleteUser(user);
            tokenAuthenticationService.deleteToken(nickname);
            return ResponseEntity.ok("User " + nickname + " was deleted");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
