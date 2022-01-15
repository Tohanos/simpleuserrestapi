package com.example.simplemessengerapp.services;

import com.example.simplemessengerapp.dto.MessageDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//  Сервис для разбора сообщений
@Service
public class MessageParserServiceImpl implements MessageParserService {

    private final MessageDbService service;
    private final UserDbService userDbService;

    public MessageParserServiceImpl(MessageDbService service, UserDbService userDbService) {
        this.service = service;
        this.userDbService = userDbService;
    }

    //  Разбор сообщения
    @Override
    public Optional<List<String>> parseMessage(MessageDto messageDto) {
        String[] strings = messageDto.getMessage().split(" ");  //  Разделяем сообщение на слова
        switch (strings[0]) {
            case "History":     //  Обрабатываем команду "History"
                if (strings.length > 1) {
                    try {
                        return Optional.of(service
                                .getMessages(Integer.parseInt(strings[1]))
                                .stream()
                                .map(message -> message.getText())
                                .collect(Collectors.toList()));
                    } catch (NumberFormatException e) {
                        return Optional.empty();
                    }
                }

        }
        return Optional.empty();    //  Если распознанных команд нет -
    }
}
