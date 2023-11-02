package com.whistleblowermanagerbe.controller;

import com.whistleblowermanagerbe.service.GestioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
