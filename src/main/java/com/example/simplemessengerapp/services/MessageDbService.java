package com.example.simplemessengerapp.services;

import com.example.simplemessengerapp.dto.MessageDto;
import com.example.simplemessengerapp.entities.Message;
import com.example.simplemessengerapp.entities.User;

import java.util.List;

public interface MessageDbService {

    Message getMessageById(Long id);
    List<Message> getMessages(User user);
    List<Message> getMessages(int maxNum);
    void storeMessage(Message message);
    void deleteMessage(Message message);

    Message fromDto(MessageDto messageDto);
    MessageDto toDto(Message message);

}
