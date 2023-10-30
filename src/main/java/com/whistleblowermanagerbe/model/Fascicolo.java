package com.whistleblowermanagerbe.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "fascicolo")
@Data
public class Fascicolo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany()
    @JoinColumn(name = "fk_fascicolo")
    private List<InfoSegnalazione> listaInfoSegnalazioni;

    private String descrizione;
    private String stato;
    private String tipologia;
    private String areaInteressata;
    private String fondatezza;
}
