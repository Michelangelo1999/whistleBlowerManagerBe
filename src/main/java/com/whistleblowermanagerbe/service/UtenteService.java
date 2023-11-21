package com.whistleblowermanagerbe.service;

import com.whistleblowermanagerbe.Enum.StatoSegnalazione;
import com.whistleblowermanagerbe.dto.ChangePasswordRequest;
import com.whistleblowermanagerbe.dto.LoginRequest;
import com.whistleblowermanagerbe.dto.NewUserRequest;
import com.whistleblowermanagerbe.dto.NumeroUtentiDto;
import com.whistleblowermanagerbe.model.*;
import com.whistleblowermanagerbe.repo.*;
import com.whistleblowermanagerbe.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UtenteService implements UserDetailsService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private RuoloRepository ruoloRepository;

    private static final Logger log = LoggerFactory.getLogger(UtenteService.class);

    @Autowired
    private RuoloUtenteRepository ruoloUtenteRepository;

    @Autowired
    private SegnalazioneRepository segnalazioneRepository;

    //questi autow sono da rimuovere dopo rimozione dati di test
    @Autowired
    private DatiUtenteRepository dur;

    @Autowired
    private SegnalazioneRepository sRepo;

    @Autowired
    private InfoSegnalazioneRepository infoRepo;

    public Utente createUtente(NewUserRequest newUserRequest){
        Optional<Utente> opt = utenteRepository.findByNomeUtente(newUserRequest.getNomeUtente());
        if(opt.isPresent()){
            return null;
        }
        Utente u = new Utente();
        u.setNome(newUserRequest.getNome());
        u.setCognome(newUserRequest.getCognome());
        u.setNomeUtente(newUserRequest.getNomeUtente());
        u.setPassword(Utility.encryptPassword(newUserRequest.getPassword()));
        u.setAbilitato(true);
        u = utenteRepository.save(u);
        RuoloUtente ru = new RuoloUtente(null, ruoloRepository.findByRuolo(newUserRequest.getRuolo()).get(), u);
        ruoloUtenteRepository.save(ru);
        return u;
    }

    public void cambiaPassword(ChangePasswordRequest changePasswordRequest) throws Exception{
        Utente u = utenteRepository.findById(changePasswordRequest.getIdUtente()).get();
        if(Utility.verifyPassword(changePasswordRequest.getVecchiaPassword(), u.getPassword())){
            u.setPassword(Utility.encryptPassword(changePasswordRequest.getNuovaPassword()));
            u.setNome(changePasswordRequest.getNome());
            u.setCognome(changePasswordRequest.getCognome());
            utenteRepository.save(u);
        } else {
            throw new Exception("Password errata");
        }
    }

    public Utente login(LoginRequest loginRequest){
        Optional<Utente> utenteOpt = utenteRepository.findByNomeUtente(loginRequest.getNomeUtente());
        if(utenteOpt.isPresent()){
            if(Utility.verifyPassword(loginRequest.getPassword(), utenteOpt.get().getPassword())){
                return utenteOpt.get();
            } else {
                return null;
            }
        }
        return null;
    }

    public List<Utente> findAllUtenti(String ruolo){
        switch (ruolo){
            case "I": ruolo = com.whistleblowermanagerbe.Enum.Ruolo.ISTRUTTORE.name();
            break;
            case "SV": ruolo = com.whistleblowermanagerbe.Enum.Ruolo.SUPERVISORE.name();
            break;
            case "CID": ruolo = com.whistleblowermanagerbe.Enum.Ruolo.CUSTODE_IDENTITA.name();
            break;
        }

        List<Utente> utenteList = utenteRepository.findAllByRoles(ruolo);
        return utenteList;
    }

    public NumeroUtentiDto countUser(){
        NumeroUtentiDto out = new NumeroUtentiDto();
        out.setNumeroI(utenteRepository.countUser(com.whistleblowermanagerbe.Enum.Ruolo.ISTRUTTORE.name()));
        out.setNumeroSv(utenteRepository.countUser(com.whistleblowermanagerbe.Enum.Ruolo.SUPERVISORE.name()));
        out.setNumeroCid(utenteRepository.countUser(com.whistleblowermanagerbe.Enum.Ruolo.CUSTODE_IDENTITA.name()));
        out.setNumeroSegnalazioni(segnalazioneRepository.countSegnalazioni());
        return out;
    }

    public void abilitaUtente(Integer idUtente, Boolean abilitato){
        utenteRepository.abilitaUtente(idUtente, abilitato);
    }

    public void cancellaUtente(Integer idUtente){
        utenteRepository.eliminaUtente(idUtente);
    }

    @PostConstruct
    public void init(){
        try {
            List<Ruolo> ruoloList = ruoloRepository.findAll();
            if (ruoloList.isEmpty()) {
                log.info("creo i ruoli");
                ruoloList.add(new Ruolo(null, null, com.whistleblowermanagerbe.Enum.Ruolo.ADMIN.name()));
                ruoloList.add(new Ruolo(null, null, com.whistleblowermanagerbe.Enum.Ruolo.ISTRUTTORE.name()));
                ruoloList.add(new Ruolo(null, null, com.whistleblowermanagerbe.Enum.Ruolo.CUSTODE_IDENTITA.name()));
                ruoloList.add(new Ruolo(null, null, com.whistleblowermanagerbe.Enum.Ruolo.SUPERVISORE.name()));
                ruoloRepository.saveAll(ruoloList);
            }
            List<Utente> utenteList = utenteRepository.findAll();
            if (utenteList.isEmpty()) {
                log.info("creo l'admin");
                Utente admin = new Utente();
                admin.setNomeUtente("admin");
                admin.setPassword(Utility.encryptPassword("whistleBlowerManager_2023!"));
                admin.setAbilitato(true);
                admin = utenteRepository.save(admin);
                RuoloUtente ponte = new RuoloUtente(null, ruoloRepository.findByRuolo(com.whistleblowermanagerbe.Enum.Ruolo.ADMIN.name()).get(), admin);
                ruoloUtenteRepository.save(ponte);

                //inserisco altri dati di test da rimuovere successivamente
                //creaUtenti();
                //creaSegnalazioni();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void creaUtenti(){
        Utente istruttore = new Utente();
        istruttore.setNomeUtente("istruttore");
        istruttore.setPassword(Utility.encryptPassword("whistleBlowerManager_2023!"));
        istruttore.setAbilitato(true);
        istruttore = utenteRepository.save(istruttore);
        RuoloUtente ponte1 = new RuoloUtente(null, ruoloRepository.findByRuolo(com.whistleblowermanagerbe.Enum.Ruolo.ISTRUTTORE.name()).get(), istruttore);
        ruoloUtenteRepository.save(ponte1);

        Utente supervisore = new Utente();
        supervisore.setNomeUtente("supervisore");
        supervisore.setPassword(Utility.encryptPassword("whistleBlowerManager_2023!"));
        supervisore.setAbilitato(true);
        supervisore = utenteRepository.save(supervisore);
        RuoloUtente ponte2 = new RuoloUtente(null, ruoloRepository.findByRuolo(com.whistleblowermanagerbe.Enum.Ruolo.SUPERVISORE.name()).get(), supervisore);
        ruoloUtenteRepository.save(ponte2);

        Utente segnalante = new Utente();
        segnalante.setNomeUtente("segnalante");
        segnalante.setPassword(Utility.encryptPassword("whistleBlowerManager_2023!"));
        segnalante.setAbilitato(true);
        utenteRepository.save(segnalante);
    }

    private void creaSegnalazioni(){
        Segnalazione s = new Segnalazione();
        s.setKey16("1234567898765432");
        s.setRuoloSegnalante("Dipendente di questa Amministrazione/Ente");
        s.setDenominazioneCompletaUfficio("SiteVenue");
        s.setRagioneSociale("SiteVenue SRL");
        s.setIndirizzoSede("FULL REMOTE");
        s.setCitta("Pignataro - Portico");
        s.setTipologiaCondottaIllecita("Da definire");
        s.setFattiAncoraInCorso(true);

        s.setElencoSoggettiCoinvolti(createSoggetto());
        s.setDescrizioneFatti("Pietro fa i pezzotti a front End per poi dare la colpa al coAdmin");
        s.setProcedimentoInAtto("Il coAdmin cerca di spiegare a Pietro il Be con scarsi risultati");
        s.setConoscenzaProcedimento("Non conosco il procedimento");
        s.setAutoritaRiferimento("Comune di Portico di Caserta");
        s.setDataEffettuazioneSegnalazione(LocalDate.now());
        s.setEstremiRegistrazione("Estremi");
        s.setDialogoParticolare("Sto creando la serra per le tartarughe");
        s.setEsitoSegnalazione("Da definire");

        s.setIdentita(createId());
        s.setAccettaTermini(true);

        s = sRepo.save(s);

        InfoSegnalazione info = new InfoSegnalazione();
        info.setDataCreazione(LocalDate.now());
        info.setUltimoAggiornamento(LocalDate.now());
        info.setStato(StatoSegnalazione.NON_ANCORA_PRESA_IN_CARICO.name());
        info.setIdentitaFornita(true);
        info.setIdentitaVerificata(false);
        info.setOggettoSegnalazione("To be Assigned");
        info.setSegnalazione(s);
        infoRepo.save(info);
    }

    private List<SoggettoCoinvolto> createSoggetto(){
        List<SoggettoCoinvolto> out = new ArrayList<>();
        SoggettoCoinvolto s = new SoggettoCoinvolto();
        s.setRagioneSocialeCognome("Natale");
        s.setNome("Pietro");
        s.setEnte("SiteVenue");
        s.setQualifica("CEO");
        s.setRuoloCoinvolgimento("Attore principale");
        s.setTelefono("3281470241");
        s.setEmail("pNatale@gmail.com");
        s.setBeneficioAccaduto("Pace interiore");
        s.setNote("Niente da aggiungere");
        out.add(s);
        return out;
    }

    private DatiUtente createId(){
        DatiUtente out = new DatiUtente();
        out.setTelefono("3281450681");
        out.setIndirizzo("Via Napoli 49");
        out.setCodicePostale("81050");
        out.setNome("Michelangelo");
        out.setMansione("CDO");
        out.setEmail("mikidenicola007@gmail.com");
        out.setCitta("Portico di Caserta");
        out.setIsAnonimo(false);
        out.setCodiceFiscale("DNCMHL99E26E932Y");
        out.setLuogoNascita("Marcianhattan");
        out.setDataNascita(LocalDate.parse("26-05-1999", Utility.FORMATTER));
        return dur.save(out);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utente userEntity = utenteRepository.findByNomeUtente(username).get();
        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new User(userEntity.getNomeUtente(), userEntity.getPassword(), new ArrayList<>());
    }

}
