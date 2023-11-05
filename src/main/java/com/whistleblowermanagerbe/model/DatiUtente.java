package com.whistleblowermanagerbe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

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
    @Column(name = "data_nascita")
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

    @OneToMany(mappedBy = "identita")
    @JsonIgnore
    private List<Segnalazione> segnalazioni;
}
