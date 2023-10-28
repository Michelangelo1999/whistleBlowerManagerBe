package com.whistleblowermanagerbe.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messaggio")
@Data
public class Messaggio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "data_ora")
    private LocalDateTime dataOra;

    private String writer;

    private String messaggio;
    @OneToOne
    @JoinColumn(name = "fk_allegato")
    private Allegato allegato;
}
