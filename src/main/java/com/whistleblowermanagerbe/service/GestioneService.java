package com.whistleblowermanagerbe.service;

import com.whistleblowermanagerbe.Enum.StatoRichiestaId;
import com.whistleblowermanagerbe.model.InfoSegnalazione;
import com.whistleblowermanagerbe.model.RichiestaIdentita;
import com.whistleblowermanagerbe.model.Segnalazione;
import com.whistleblowermanagerbe.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class GestioneService {

    @Autowired
    private FascicoloRepository fascicoloRepository;

    @Autowired
    private SegnalazioneRepository segnalazioneRepository;

    @Autowired
    private InfoSegnalazioneRepository infoSegnalazioneRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private RichiestaIdentitaRepository richiestaIdentitaRepository;

    @Autowired
    private DatiUtenteRepository datiUtenteRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    public RichiestaIdentita addRichiestaIdentita(Integer idSegnalazione, String messaggio){
        RichiestaIdentita ri = new RichiestaIdentita();
        ri.setDataRichiesta(LocalDate.now());
        ri.setMessaggio(messaggio);
        ri.setStato(StatoRichiestaId.IN_ATTESA.name());
        ri.setSegnalazione(segnalazioneRepository.findById(idSegnalazione).get());
        return richiestaIdentitaRepository.save(ri);
    }

    public RichiestaIdentita updateRichiestaIdentita(RichiestaIdentita richiestaIdentita){
        return richiestaIdentitaRepository.save(richiestaIdentita);
    }

    public void assegnaSegnalazione(Integer idSegnalazione, Integer idUtente){
        InfoSegnalazione i = infoSegnalazioneRepository.findById(idSegnalazione).get();
        i.setAssegnatario(utenteRepository.findById(idUtente).get());
        infoSegnalazioneRepository.save(i);
    }
}
