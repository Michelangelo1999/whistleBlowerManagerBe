package com.whistleblowermanagerbe.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "soggetto_coinvolto")
@Data
public class SoggettoCoinvolto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "ragione_sociale_cognome")
    private String ragioneSocialeCognome;
    private String nome;
    private String ente;
    private String qualifica;   //picklist
    @Column(name = "ruolo_coinvolgimento")
    private String ruoloCoinvolgimento;
    private String telefono;
    private String email;
    @Column(name = "beneficio_accaduto")
    private String beneficioAccaduto;   //picklist
    @Lob
    private String note;
}
