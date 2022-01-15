package com.example.simplemessengerapp.services;

import com.example.simplemessengerapp.dto.MessageDto;
import com.example.simplemessengerapp.entities.Message;
import com.example.simplemessengerapp.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MessageServicesTest {

    @Autowired
    private MessageDbService messageDbService;
    @Autowired
    private UserDbService userDbService;
    @Autowired
    private MessageParserService parserService;

    private final String TEXT = "Test message";
    private final String HISTORY_MSG = "History 1";

    @Test
    public void messageCreateDeleteTest() {
        User user = userDbService.getUserById(1L);
        Message message = new Message();
        setMessage(user, message);

        messageDbService.storeMessage(message);

        List<Message> messages = messageDbService.getMessages(user);
        assertThat(messages.contains(message)).isTrue();

        messageDbService.deleteMessage(message);

        messages = messageDbService.getMessages(user);
        assertThat(messages.contains(message)).isFalse();

    }

    @Test
    public void messageParseTest() {
        User user = userDbService.getUserById(1L);
        Message message = new Message();
        setMessage(user, message);

        messageDbService.storeMessage(message);

        MessageDto messageDto = new MessageDto(user.getName(), HISTORY_MSG);
        Optional<List<String>> messages = parserService.parseMessage(messageDto);

        assertThat(messages.get().get(0)).isEqualTo(TEXT);

        messageDbService.deleteMessage(message);

    }

    private void setMessage(User user, Message message) {
        message.setUser(user);
        message.setText(TEXT);
        message.setDt(Timestamp.valueOf(LocalDateTime.now()));
    }

}
