package com.example.simpleuserrestapi.repositories;

import com.example.simpleuserrestapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserByName(String name);
    Optional<User> getUserByNickname(String nickname);
}
