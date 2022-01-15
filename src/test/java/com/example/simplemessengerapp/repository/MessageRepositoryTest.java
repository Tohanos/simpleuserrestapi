package com.example.simplemessengerapp.repository;

import com.example.simplemessengerapp.entities.Message;
import com.example.simplemessengerapp.entities.User;
import com.example.simplemessengerapp.repositories.MessageRepository;
import com.example.simplemessengerapp.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class MessageRepositoryTest {

    private final String TEXT = "Hello there!";
    private final Timestamp TIMESTAMP = Timestamp.valueOf(LocalDateTime.now());

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testMessage() {
        User user = userRepository.getById(1L);

        Message message = new Message();
        message.setText(TEXT);
        message.setUser(user);
        message.setDt(TIMESTAMP);

        saveMessage(message);

        List<Message> messages = messageRepository.findAllByUser(user);

        assertThat(messages.contains(message));

        deleteMessage(message);

    }

    @Transactional
    public void saveMessage(Message message) {
        messageRepository.save(message);
    }

    @Transactional
    public void deleteMessage(Message message) {
        messageRepository.delete(message);
    }
}
