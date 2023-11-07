package com.whistleblowermanagerbe.service;

import com.whistleblowermanagerbe.Enum.StatoRichiestaId;
import com.whistleblowermanagerbe.Enum.StatoSegnalazione;
import com.whistleblowermanagerbe.dto.*;
import com.whistleblowermanagerbe.model.*;
import com.whistleblowermanagerbe.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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

    public void cambiaStatoRichiesta(Integer idRichiesta, String stato){
        RichiestaIdentita ri = richiestaIdentitaRepository.findById(idRichiesta).get();
        ri.setStato(stato);
        richiestaIdentitaRepository.save(ri);
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

    public ChatAsincronaDto getChat(Integer idInfoSegnalazione){
        Optional<ChatAsincrona> chatOpt =  chatRepository.findByInfoSegnalazione(idInfoSegnalazione);
        if(chatOpt.isPresent()){
            return convert(chatOpt.get());
        } else {
            return null;
        }
    }

    private ChatAsincronaDto convert(ChatAsincrona model){
        ChatAsincronaDto dto = new ChatAsincronaDto();
        dto.setMessaggi(new ArrayList<>());
        dto.setInfoSegnalazione(model.getInfoSegnalazione().getId());
        dto.setId(model.getId());
        for(Messaggio m : model.getMessaggi()){
            dto.getMessaggi().add(new MessaggioDto(m.getId(),m.getIdWriter(), m.getMessaggio(), m.getAllegato() != null ? m.getAllegato().getId() : null, null));
        }
        return dto;
    }
    public void addMessage(Integer idInfoSegnalazione, Integer idUtente, MessaggioDto messaggio){
        Optional<ChatAsincrona> chatOpt =  chatRepository.findByInfoSegnalazione(idInfoSegnalazione);
        if (chatOpt.isPresent()){
            ChatAsincrona chat = chatOpt.get();
            if(chat.getMessaggi() == null){
                chat.setMessaggi(new ArrayList<>());
            }
            chat.getMessaggi().add(convert(messaggio, idUtente, idInfoSegnalazione));
            chatRepository.save(chat);
        } else {
            ChatAsincrona chat = new ChatAsincrona();
            chat.setInfoSegnalazione(infoSegnalazioneRepository.findById(idInfoSegnalazione).get());
            chat.setMessaggi(new ArrayList<>());
            chat.getMessaggi().add(convert(messaggio, idUtente, idInfoSegnalazione));
            chatRepository.save(chat);
        }
    }

    private Messaggio convert(MessaggioDto dto, Integer idUtente, Integer idInfo){
        Messaggio m = new Messaggio();
        if(dto.getAllegato() != null){
            Allegato a = createAllegatoChat(dto.getAllegato(), idInfo);
            m.setAllegato(a);
        }
        m.setDataOra(LocalDateTime.now());
        m.setIdWriter(idUtente);
        m.setMessaggio(dto.getMessaggio());
        return m;
    }

    private Allegato createAllegatoChat(String allegato, Integer idInfo){
        Allegato a = new Allegato();
        a.setDescrizione("Allegato chat");
        a.setAllegato(Base64.getDecoder().decode(allegato));
        a.setInfoSegnalazioneAllegato(infoSegnalazioneRepository.findById(idInfo).get());
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

    public FascicoloDto createFascicolo(FascicoloDto newFascicolo){
        Fascicolo f = new Fascicolo();
        f.setDescrizione(newFascicolo.getDescrizione());
        f.setFondatezza(newFascicolo.getFondatezza());
        f.setStato(newFascicolo.getStato());
        f.setTipologia(newFascicolo.getTipologia());
        f.setAreaInteressata(newFascicolo.getAreaInteressata());
        f.setListaInfoSegnalazioni(new ArrayList<>());
        f.getListaInfoSegnalazioni().add(infoSegnalazioneRepository.findById(newFascicolo.getIdPrimaSegnalazioneInfo()).get());
        return convert(fascicoloRepository.save(f));
    }

    public FascicoloDto updateFascicolo(FascicoloDto in){
        Fascicolo f = fascicoloRepository.findById(in.getId()).get();
        f.setDescrizione(in.getDescrizione());
        f.setFondatezza(in.getFondatezza());
        f.setStato(in.getStato());
        f.setTipologia(in.getTipologia());
        f.setAreaInteressata(in.getAreaInteressata());
        return convert(fascicoloRepository.save(f));
    }

    public void addSegnalazioneInFascicolo(Integer idInfo, Integer idFascicolo){
        infoSegnalazioneRepository.addSegnalazioneInFascicolo(idFascicolo, idInfo);
    }

    public FascicoloDto getFascicoloById(Integer id){
        Fascicolo f = fascicoloRepository.findById(id).get();
        FascicoloDto out = convert(f);
        return out;
    }

    public List<FascicoloDto> getAllFascicoli(){
        List<FascicoloDto> out = new ArrayList<>();
        List<Fascicolo> db = fascicoloRepository.findAll();
        for(Fascicolo f : db){
            out.add(convert(f));
        }
        return out;
    }

    public List<InfoSegnalazione> getSegnalazioniByFascicolo(Integer idFascicolo){
        return infoSegnalazioneRepository.findAllByFascicolo(idFascicolo);
    }

    public FascicoloDto getFascicoloBySegnalazioneId(Integer idInfo){
        Optional<Fascicolo> opt = fascicoloRepository.findByInfoSegnalazione(idInfo);
        if(opt.isPresent()){
            return convert(opt.get());
        } else {
            return null;
        }
    }

    public InfoSegnalazione getInfoById(Integer id){
        InfoSegnalazione is = infoSegnalazioneRepository.findById(id).get();
        int giorniTrascorsi = (int)ChronoUnit.DAYS.between(is.getDataCreazione(), LocalDate.now());
        is.setGiorniAllaScadenza(giorniTrascorsi <= 10 ? 10-giorniTrascorsi : 0);
        return is;
    }

    public byte[] downloadAllegato(Integer id) throws IOException {
        Allegato allegato = allegatoRepository.findById(id).get();

        return allegato.getAllegato();
    }

    private FascicoloDto convert(Fascicolo f){
        FascicoloDto out = new FascicoloDto();
        out.setDescrizione(f.getDescrizione());
        out.setId(f.getId());
        out.setTipologia(f.getTipologia());
        out.setFondatezza(f.getFondatezza());
        out.setAreaInteressata(f.getAreaInteressata());
        out.setStato(f.getStato());
        return out;
    }

    public List<AllegatoDto> getAllegatiByInfo(Integer idInfo){
        List<Allegato> modelList = allegatoRepository.findAllByInfoId(idInfo);
        List<AllegatoDto> out = new ArrayList<>();
        for(Allegato a : modelList){
            out.add(new AllegatoDto(a.getId(), a.getDescrizione()));
        }
        return out;
    }
}
