package com.whistleblowermanagerbe.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "ruolo_utente")
@Data
public class RuoloUtente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "fk_ruolo")
    private Ruolo ruolo;

    @ManyToOne()
    @JoinColumn(name = "fk_utente")
    private Utente utente;
}
