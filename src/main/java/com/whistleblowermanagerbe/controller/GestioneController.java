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

    @GetMapping(value = "cambiaStatoRichiesta/{idRichiesta}/{stato}")
    public ResponseEntity<?> cambiaStatoRichiesta(@PathVariable(name = "idRichiesta") Integer idRichiesta, @PathVariable(name = "stato") String stato){
        try{
            gestioneService.cambiaStatoRichiesta(idRichiesta, stato);
            return ResponseEntity.ok().build();
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "cambiaStato/{idInfo}/{stato}")
    public ResponseEntity<?> cambiaStato(@PathVariable(name = "idInfo") Integer idInfo, @PathVariable(name = "stato") String stato){
        try{
            gestioneService.changeStato(idInfo, stato);
            return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "cercaRichiestaSegnalazione/{idSegnalazione}")
    public ResponseEntity<?> cercaRichiestaSegnalazione(@PathVariable(name = "idSegnalazione") Integer idSegnalazione){
        return ResponseEntity.ok(gestioneService.cercaRichiesta(idSegnalazione));
    }
}
