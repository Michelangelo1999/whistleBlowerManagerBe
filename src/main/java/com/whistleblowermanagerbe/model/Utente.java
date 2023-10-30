package com.whistleblowermanagerbe.model;

import lombok.Data;


import javax.persistence.*;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "utente")
@Data
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @OneToMany(mappedBy = "utente")
    private List<RuoloUtente> ruoloList;

    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String nomeUtente;
    private Boolean cancellato;
    private Boolean abilitato;

}
