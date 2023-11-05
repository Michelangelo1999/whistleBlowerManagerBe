package com.whistleblowermanagerbe.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RichiestaIdentitaDto {
    private Integer id;
    private String nomeIstruttore;
    private String cognomeIstruttore;
    private LocalDate dataRichiesta;
    private String messaggio;
    private String stato;
    private Integer idSegnalazione;
}
