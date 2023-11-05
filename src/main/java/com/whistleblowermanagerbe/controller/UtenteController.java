package com.whistleblowermanagerbe.controller;

import com.whistleblowermanagerbe.dto.ChangePasswordRequest;
import com.whistleblowermanagerbe.dto.NewUserRequest;
import com.whistleblowermanagerbe.repo.UtenteRepository;
import com.whistleblowermanagerbe.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping(value = "private/utente")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @GetMapping(value = "getAllUtenti/{ruolo}")
    public ResponseEntity<?> getAllUtenti(@PathVariable(name = "ruolo") String ruolo){
        return ResponseEntity.ok(utenteService.findAllUtenti(ruolo));
    }

    @GetMapping(value = "countUser")
    public ResponseEntity<?> countUser(){
        return ResponseEntity.ok(utenteService.countUser());
    }

    @Transactional
    @GetMapping(value = "disabilitaAbilitaUtente/{idUtente}/{abilitato}")
    public ResponseEntity<?> disabilitaAbilitaUtente(@PathVariable(name = "idUtente") Integer idUtente, @PathVariable(name = "abilitato") Boolean abilitato){
        try {
            utenteService.abilitaUtente(idUtente, abilitato);
            return ResponseEntity.ok().build();
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "createUtente")
    public ResponseEntity<?> createUtente(@RequestBody NewUserRequest newUserRequest){
        return ResponseEntity.ok(utenteService.createUtente(newUserRequest));
    }

    @PostMapping(value = "modificaPassword")
    public ResponseEntity<?> modificaPassword(@RequestBody ChangePasswordRequest changePasswordRequest){
        try {
            utenteService.cambiaPassword(changePasswordRequest);
            return ResponseEntity.ok().build();
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "cancellaUtente/{idUtente}")
    public ResponseEntity<?> cancellaUtente(@PathVariable(name = "idUtente") Integer idUtente){
        try{
            utenteService.cancellaUtente(idUtente);
            return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
