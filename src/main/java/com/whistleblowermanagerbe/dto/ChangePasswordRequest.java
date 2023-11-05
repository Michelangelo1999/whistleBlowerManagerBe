package com.whistleblowermanagerbe.dto;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private Integer idUtente;
    private String nome;
    private String cognome;
    private String vecchiaPassword;
    private String nuovaPassword;
}
