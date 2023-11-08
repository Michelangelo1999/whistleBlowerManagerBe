package com.whistleblowermanagerbe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne()
    @JoinColumn(name = "fk_info_segnalazione")
    @JsonIgnore
    private InfoSegnalazione fkInfoSegnalazione;
}
