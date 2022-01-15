package com.example.simplemessengerapp.repositories;

import com.example.simplemessengerapp.entities.Message;
import com.example.simplemessengerapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByUser(User user);
//    List<Message> findTopByOrderByDtDesc(User user, int limit);
}
