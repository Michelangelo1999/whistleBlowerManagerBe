package com.whistleblowermanagerbe.service;

import com.whistleblowermanagerbe.Enum.StatoRichiestaId;
import com.whistleblowermanagerbe.Enum.StatoSegnalazione;
import com.whistleblowermanagerbe.dto.CercaRichiestaIdDto;
import com.whistleblowermanagerbe.dto.MessaggioDto;
import com.whistleblowermanagerbe.dto.RichiestaIdentitaDto;
import com.whistleblowermanagerbe.model.*;
import com.whistleblowermanagerbe.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private AllegatoRepository allegatoRepository;

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
        i.setStato(StatoSegnalazione.PRESA_IN_CARICO.name());
        infoSegnalazioneRepository.save(i);
    }

    public void changeStato(Integer idInfoSegnalazione, String nuovoStato){
        InfoSegnalazione i = infoSegnalazioneRepository.findById(idInfoSegnalazione).get();
        i.setStato(nuovoStato);
        infoSegnalazioneRepository.save(i);
    }

    public ChatAsincrona getChat(Integer idInfoSegnalazione){
        Optional<ChatAsincrona> chatOpt =  chatRepository.findByInfoSegnalazione(idInfoSegnalazione);
        return chatOpt.orElse(null);
    }

    public void addMessage(Integer idInfoSegnalazione, Integer idUtente, MessaggioDto messaggio){
        Optional<ChatAsincrona> chatOpt =  chatRepository.findByInfoSegnalazione(idInfoSegnalazione);
        if (chatOpt.isPresent()){
            ChatAsincrona chat = chatOpt.get();
            if(chat.getMessaggi() == null){
                chat.setMessaggi(new ArrayList<>());
            }
            chat.getMessaggi().add(convert(messaggio, idUtente));
            chatRepository.save(chat);
        } else {
            ChatAsincrona chat = new ChatAsincrona();
            chat.setMessaggi(new ArrayList<>());
            chat.getMessaggi().add(convert(messaggio, idUtente));
            chatRepository.save(chat);
        }
    }

    private Messaggio convert(MessaggioDto dto, Integer idUtente){
        Messaggio m = new Messaggio();
        if(dto.getAllegato() != null){
            Allegato a = createAllegatoChat(dto.getAllegato());
            m.setAllegato(a);
        }
        m.setDataOra(LocalDateTime.now());
        m.setIdWriter(idUtente);
        m.setMessaggio(dto.getMessaggio());
        return m;
    }

    private Allegato createAllegatoChat(String allegato){
        Allegato a = new Allegato();
        a.setDescrizione("Allegato chat");
        a.setAllegato(Base64.getDecoder().decode(allegato));
        return allegatoRepository.save(a);
    }

    public List<InfoSegnalazione> getAllSegnalazioniNonAssegnate(){
        return infoSegnalazioneRepository.findAllNonAssegnate();
    }

    public List<InfoSegnalazione> getAllSegnalazioni(){
        return infoSegnalazioneRepository.findAll();
    }

    public List<InfoSegnalazione> getAllSegnalazioni(Integer idIstruttore){
        return infoSegnalazioneRepository.findAllAssegnate(idIstruttore);
    }

    public List<RichiestaIdentitaDto> findAllRichiesteId(){
        List<RichiestaIdentita> listaRichieste = richiestaIdentitaRepository.findAll();
        return convert(listaRichieste);
    }

    private List<RichiestaIdentitaDto> convert (List<RichiestaIdentita> input){
        List<RichiestaIdentitaDto> out = new ArrayList<>();
        for (RichiestaIdentita r : input){
            RichiestaIdentitaDto dto = new RichiestaIdentitaDto();
            dto.setId(r.getId());
            dto.setStato(r.getStato());
            dto.setDataRichiesta(r.getDataRichiesta());
            dto.setIdSegnalazione(r.getSegnalazione().getId());
            dto.setMessaggio(r.getMessaggio());
            dto.setNomeIstruttore(utenteRepository.findNomeBySegnalazione(r.getSegnalazione().getId()));
            dto.setCognomeIstruttore(utenteRepository.findCognomeBySegnalazione(r.getSegnalazione().getId()));
            out.add(dto);
        }
        return out;
    }

    public CercaRichiestaIdDto cercaRichiesta(Integer idSegnalazione){
        CercaRichiestaIdDto out = new CercaRichiestaIdDto();
        Optional<RichiestaIdentita> richiestaIdentitaOpt = richiestaIdentitaRepository.findBySegnalazione(idSegnalazione);
        if(richiestaIdentitaOpt.isPresent()){
            out.setRichiesta(richiestaIdentitaOpt.get());
            if(out.getRichiesta().getStato().equalsIgnoreCase(StatoRichiestaId.APPROVATA.name())){
                DatiUtente datiUtente = datiUtenteRepository.findBySegnalazione(idSegnalazione);
                out.setIdentita(datiUtente);
            }
            return out;
        } else{
            return out;
        }
    }
}
