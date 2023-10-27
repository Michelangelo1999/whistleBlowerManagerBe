package com.whistleblowermanagerbe.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ruolo")
@Data
public class Ruolo {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @OneToMany(mappedBy = "ruolo")
    private List<RuoloUtente> utenteList;

    private String ruolo;
}
