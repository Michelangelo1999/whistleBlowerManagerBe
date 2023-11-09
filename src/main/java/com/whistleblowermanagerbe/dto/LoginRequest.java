package com.whistleblowermanagerbe.dto;

import lombok.Data;

@Data
public class LoginRequest {

    private String nomeUtente;
    private String password;
    private String key;
}
