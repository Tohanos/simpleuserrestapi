package com.example.simplemessengerapp.controllers;

import com.example.simplemessengerapp.dto.MessageDto;
import com.example.simplemessengerapp.services.MessageDbService;
import com.example.simplemessengerapp.services.MessageParserService;
import com.example.simplemessengerapp.services.TokenAuthenticationService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/*
    REST-контроллер для сообщений
 */
@RestController
public class MessageController {

    //    Зависимости
    private final MessageDbService messageDbService;
    private final MessageParserService messageParserService;
    private final TokenAuthenticationService tokenAuthenticationService;

    //  Константы
    private final static String MESSAGE_HEADER = "Bearer";

    //Внедряем зависимости через конструктор
    public MessageController(MessageDbService messageDbService, MessageParserService messageParserService, TokenAuthenticationService tokenAuthenticationService) {
        this.messageDbService = messageDbService;
        this.messageParserService = messageParserService;
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    //  Обрабатываем POST запрос на эндпоинт /message
    @PostMapping("/message")
    public ResponseEntity<String> receiveMessage(@RequestHeader(MESSAGE_HEADER) String auth, @RequestBody MessageDto messageDto) {
        if (tokenAuthenticationService.checkToken(messageDto.getName(), auth)) {    //   авторизуемся
            if (messageParserService.parseMessage(messageDto).isPresent()) {        //  В теле сообщения есть команды
                return ResponseEntity.ok(messageParserService.parseMessage(messageDto).get().toString());   //Возвращаем результаты
            }
            messageDbService.storeMessage(messageDbService.fromDto(messageDto));    //  команд нет, сохраняем сообщение
            return ResponseEntity.ok("Alright");
        }
        return ResponseEntity.status(401).body("Bad token");    //  авторизация не прошла
    }

}
