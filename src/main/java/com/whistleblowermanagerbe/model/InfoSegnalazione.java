package com.whistleblowermanagerbe.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "info_segnalazione")
@Data
public class InfoSegnalazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "data_creazione")
    private LocalDate dataCreazione;
    @ManyToOne
    @JoinColumn(name = "fk_istruttore")
    private Utente assegnatario;

    @OneToOne
    @JoinColumn(name = "fk_segnalazione")
    private Segnalazione segnalazione;

    @Column(name = "ultimo_aggiornamento")
    private LocalDate ultimoAggiornamento;

    private String stato;
    @OneToMany(mappedBy = "infoSegnalazioneAllegato")
    private List<Allegato> fileAllegati;
    @Column(name = "identita_fornita")
    private Boolean identitaFornita;

    @Column(name = "identita_verificata")
    private Boolean identitaVerificata;
    @Column(name = "oggetto_segnalazione")
    private String oggettoSegnalazione;
    @OneToMany(mappedBy = "fkInfoSegnalazione")
    private List<Commento> commenti;

    private Integer novantesimi;
    @Transient
    private Integer giorniAllaScadenza;
}
