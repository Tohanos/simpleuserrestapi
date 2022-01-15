package com.example.simpleuserrestapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    private String nickname;
    private String name;
    private String surname1;
    private String surname2;
    private String birthday;
    private String email;
    private String phone;
    private String password;


}
