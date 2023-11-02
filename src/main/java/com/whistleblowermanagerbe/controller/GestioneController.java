package com.whistleblowermanagerbe.controller;

import com.whistleblowermanagerbe.service.GestioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "private/gestione")
public class GestioneController {

    @Autowired
    private GestioneService gestioneService;

    @GetMapping(value = "getAllSegnalazioni")
    public ResponseEntity<?> getAllSegnalazioni(){
        return ResponseEntity.ok(gestioneService.getAllSegnalazioni());
    }

    @GetMapping(value = "getAllSegnalazioni/{idIstruttore}")
    public ResponseEntity<?> getAllSegnalazioni(@PathVariable(name = "idIstruttore") Integer idIstruttore){
        return ResponseEntity.ok(gestioneService.getAllSegnalazioni(idIstruttore));
    }

    @GetMapping(value = "getAllSegnalazioniNonAssegnate")
    public ResponseEntity<?> getAllSegnalazioniNonAssegnate(){
        return ResponseEntity.ok(gestioneService.getAllSegnalazioniNonAssegnate());
    }

    @GetMapping(value = "getAllRichiesteId")
    public ResponseEntity<?> getAllRichiesteId(){
        return ResponseEntity.ok(gestioneService.findAllRichiesteId());
    }

    @GetMapping(value = "assegnaSegnalazione/{fkIstruttore}/{idSegnalazione}")
    public ResponseEntity<?> assegnaSegnalazione(@PathVariable(name = "fkIstruttore")Integer fkIstruttore, @PathVariable(name = "idSegnalazione")Integer idSegnalazione){
        try{
            gestioneService.assegnaSegnalazione(idSegnalazione, fkIstruttore);
            return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "addRichiestaIdentita/{idSegnalazione}")
    public ResponseEntity<?> addRichiestaIdentita(@PathVariable(name = "idSegnalazione") Integer idSegnalazione, @RequestBody String messaggio){
        try{
            return ResponseEntity.ok(gestioneService.addRichiestaIdentita(idSegnalazione, messaggio));
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
