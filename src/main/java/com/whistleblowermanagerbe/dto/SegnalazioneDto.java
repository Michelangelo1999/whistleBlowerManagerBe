package com.whistleblowermanagerbe.dto;

import com.whistleblowermanagerbe.model.Allegato;
import com.whistleblowermanagerbe.model.DatiUtente;
import com.whistleblowermanagerbe.model.SoggettoCoinvolto;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
public class SegnalazioneDto {

    private Integer id;
    private String key16;

    //sezione 1
    private String ruoloSegnalante;
    //sezione 1 - datore di lavoro
    private String denominazioneCompletaUfficio;
    private String ragioneSociale;
    private String indirizzoSede;
    private String citta;

    //sezione 2
    private String tipologiaCondottaIllecita;  //sono più stringhe separate da una virgola, ma arriva e viene rest come lista di stringhe
    private String dataAvvenimentoFatti;
    private Boolean fattiAncoraInCorso;

    //sezione 2 - elenco soggetti coinvolti
    private List<SoggettoCoinvoltoDto> elencoSoggettiCoinvolti;
    private String descrizioneFatti;

    //sezione 3 - altri soggetti informati
    private String procedimentoInAtto;
    private String conoscenzaProcedimento;    //picklist
    // 3 - procedimento a seguito di mia segnalazione

    private String autoritaRiferimento;    //picklist
    private String dataEffettuazioneSegnalazione;
    private String estremiRegistrazione;
    private String dialogoParticolare;
    private String esitoSegnalazione;
    private MultipartFile copiaEsposto;

    private MultipartFile evidenzeDoc;
    private String decsrizioneEvidenzeDoc;
    private MultipartFile evidenzeMultimediali;
    private String decsrizioneEvidenzeMultim;
    private Boolean isAnonimo;

    private String nome;
    private String cognome;
    private String dataNascita;
    private String luogoNascita;
    private String codiceFiscale;
    private String indirizzo;
    private String cittaSegnalante;
    private String codicePostale;
    private String telefono;
    private String email;
    private String mansione;

    private Boolean accettaTermini;
}
