package com.example.simplemessengerapp.services;

import com.example.simplemessengerapp.dto.MessageDto;
import com.example.simplemessengerapp.entities.Message;

import java.util.List;
import java.util.Optional;

public interface MessageParserService {
    Optional<List<String>> parseMessage(MessageDto messageDto);
}
