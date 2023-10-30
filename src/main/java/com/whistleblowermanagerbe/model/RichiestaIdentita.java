package com.whistleblowermanagerbe.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "richiesta_identita")
@Data
public class RichiestaIdentita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "fk_segnalazione")
    private Segnalazione segnalazione;

    private LocalDate dataRichiesta;
    private String messaggio;
    private String stato;
}
