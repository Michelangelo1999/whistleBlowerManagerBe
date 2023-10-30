package com.whistleblowermanagerbe.service;

import com.whistleblowermanagerbe.dto.ChangePasswordRequest;
import com.whistleblowermanagerbe.dto.NewUserRequest;
import com.whistleblowermanagerbe.model.Ruolo;
import com.whistleblowermanagerbe.model.RuoloUtente;
import com.whistleblowermanagerbe.model.Utente;
import com.whistleblowermanagerbe.repo.RuoloRepository;
import com.whistleblowermanagerbe.repo.RuoloUtenteRepository;
import com.whistleblowermanagerbe.repo.UtenteRepository;
import com.whistleblowermanagerbe.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private RuoloRepository ruoloRepository;

    private static final Logger log = LoggerFactory.getLogger(UtenteService.class);

    @Autowired
    private RuoloUtenteRepository ruoloUtenteRepository;

    public Utente createUtente(NewUserRequest newUserRequest){
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
        } else {
            throw new Exception("Password errata");
        }
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
                admin.setPassword("whistleBlowerManager_2023!");
                admin.setAbilitato(true);
                admin = utenteRepository.save(admin);
                RuoloUtente ponte = new RuoloUtente(null, ruoloRepository.findByRuolo(com.whistleblowermanagerbe.Enum.Ruolo.ADMIN.name()).get(), admin);
                ruoloUtenteRepository.save(ponte);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }


}
