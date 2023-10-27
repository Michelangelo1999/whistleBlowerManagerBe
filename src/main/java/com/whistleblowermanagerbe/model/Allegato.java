package com.whistleblowermanagerbe.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "allegato")
@Data
public class Allegato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private byte[] allegato;
    @Lob
    private String descrizione;

}
