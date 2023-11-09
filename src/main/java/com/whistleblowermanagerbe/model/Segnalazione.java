package com.whistleblowermanagerbe.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "segnalazione")
@Data
public class Segnalazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "key_16")
    private String key16;

    private String stato;
    private Integer novantesimi;

    //sezione 1
    @Column(name = "ruolo_segnalante")
    private String ruoloSegnalante;

    //sezione 1 - datore di lavoro
    @Column(name = "denominazione_completa_ufficio")
    private String denominazioneCompletaUfficio;

    @Column(name = "ragione_sociale")
    private String ragioneSociale;

    @Column(name = "indirizzo_sede")
    private String indirizzoSede;

    private String citta;

    //sezione 2
    @Lob
    //@Column(name = "tipologia_condotta_illecita")
    private String tipologiaCondottaIllecita;

    //@Column(name = "data_avvenimento_fatti")
    private LocalDate dataAvvenimentoFatti;

    //@Column(name = "fatti_ancora_in_corso")
    private Boolean fattiAncoraInCorso;

    //sezione 2 - elenco soggetti coinvolti
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_segnalazione")
    private List<SoggettoCoinvolto> elencoSoggettiCoinvolti;
    @Lob
    private String descrizioneFatti;

    //sezione 3 - altri soggetti informati
    //@Column(name = "procedimento_atto")
    private String procedimentoInAtto;
    //@Column(name = "conoscenza procedimento")
    private String conoscenzaProcedimento;    //picklist
    // 3 - procedimento a seguito di mia segnalazione
    //@Column(name = "autorita_riferimento")
    private String autoritaRiferimento;    //picklist
    //@Column(name = "data_effettuazione_segnalazione")
    private LocalDate dataEffettuazioneSegnalazione;
    //@Column(name = "estremi_registrazione")
    private String estremiRegistrazione;

    //@Column(name = "dialogo_particolare")
    private String dialogoParticolare;
    //@Column(name = "esito_segnalazione")
    private String esitoSegnalazione;

    @OneToOne
    @JoinColumn(name = "fk_copia_esposto", referencedColumnName = "id")
    private Allegato copiaEsposto;

    @OneToOne
    @JoinColumn(name = "fk_doc", referencedColumnName = "id")
    private Allegato evidenzeDocumentali;

    @OneToOne()
    @JoinColumn(name = "fk_multimedia", referencedColumnName = "id")
    private Allegato evidenzeMultimediali;
    @ManyToOne
    @JoinColumn(name = "fk_dati_utente")
    private DatiUtente identita;
    //@Column(name = "accetta_termini")
    private Boolean accettaTermini;


}
