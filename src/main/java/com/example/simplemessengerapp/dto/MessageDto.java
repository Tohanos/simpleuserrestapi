package com.example.simplemessengerapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MessageDto {
    private String name;
    private String message;

    @Override
    public String toString() {
        return "MessageDto{" +
                "name='" + name + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
