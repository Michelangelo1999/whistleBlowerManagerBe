package com.whistleblowermanagerbe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ruolo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ruolo {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToMany(mappedBy = "ruolo")
    @JsonIgnore
    private List<RuoloUtente> utenteList;

    private String ruolo;
}
