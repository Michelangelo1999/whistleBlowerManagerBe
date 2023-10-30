package com.whistleblowermanagerbe.dto;

import lombok.Data;

@Data
public class NewUserRequest {

    private String nome;
    private String cognome;
    private String email;
    private String nomeUtente;
    private String password;
    private String ruolo;
}
