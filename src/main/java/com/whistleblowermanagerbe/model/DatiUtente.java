package com.whistleblowermanagerbe.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "dati_utente")
@Data
public class DatiUtente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "is_anonimo")
    private Boolean isAnonimo;

    private String nome;
    private String cognome;
    @Column(name = "is_anonimo")
    private LocalDate dataNascita;
    @Column(name = "luogo_nascita")
    private String luogoNascita;
    @Column(name = "codice_fiscale")
    private String codiceFiscale;
    private String indirizzo;
    private String citta;
    @Column(name = "codice_postale")
    private String codicePostale;
    private String telefono;
    private String email;
    private String mansione;
}
