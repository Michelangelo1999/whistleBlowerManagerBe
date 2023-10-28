package com.whistleblowermanagerbe.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "commento")
@Data
public class Commento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String commento;
}
