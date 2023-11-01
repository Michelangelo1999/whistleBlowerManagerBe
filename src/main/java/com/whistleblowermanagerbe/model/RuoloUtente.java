package com.whistleblowermanagerbe.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ruolo_utente")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RuoloUtente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "fk_ruolo")
    private Ruolo ruolo;

    @ManyToOne()
    @JoinColumn(name = "fk_utente")
    @JsonIgnore
    private Utente utente;
}
