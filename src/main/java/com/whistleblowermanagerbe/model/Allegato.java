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
    @Lob
    private byte[] allegato;
    @Lob
    private String descrizione;

    @ManyToOne()
    @JoinColumn(name = "fk_info_segnalazione")
    private InfoSegnalazione infoSegnalazioneAllegato;

}
