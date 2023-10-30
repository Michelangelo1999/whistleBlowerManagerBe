package com.whistleblowermanagerbe.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ruolo")
@Data
@AllArgsConstructor
public class Ruolo {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToMany(mappedBy = "ruolo")
    private List<RuoloUtente> utenteList;

    private String ruolo;
}
