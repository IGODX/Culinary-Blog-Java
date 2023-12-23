package com.example.culinaryblog.DTOs;

import lombok.Data;

@Data
public class RegistrationDTO {
    private String username;

    private String email;

    private String password;
    private String repeatPassword;
}
