package com.whistleblowermanagerbe.controller;

import com.whistleblowermanagerbe.dto.AllegatoDto;
import com.whistleblowermanagerbe.dto.FascicoloDto;
import com.whistleblowermanagerbe.dto.MessaggioDto;
import com.whistleblowermanagerbe.model.Commento;
import com.whistleblowermanagerbe.service.GestioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

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

    @PostMapping(value = "createFascicolo")
    public ResponseEntity<?> createFascicolo(@RequestBody FascicoloDto f){
        FascicoloDto out = gestioneService.createFascicolo(f);
        return ResponseEntity.ok(out);
    }

    @PostMapping(value = "updateFascicolo")
    public ResponseEntity<?> updateFascicolo(@RequestBody FascicoloDto f){
        return ResponseEntity.ok(gestioneService.updateFascicolo(f));
    }

    @Transactional
    @GetMapping(value = "addSegnalazioneInFascicolo/{idSegnalazione}/{idFascicolo}")
    public ResponseEntity<?> addSegnalazioneInFascicolo(@PathVariable(name = "idSegnalazione") Integer idSegnalazione, @PathVariable(name = "idFascicolo") Integer idFascicolo){
        try{
            gestioneService.addSegnalazioneInFascicolo(idSegnalazione, idFascicolo);
            return ResponseEntity.ok().build();
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "getFascicoloById/{idFascicolo}")
    public ResponseEntity<?> getFascicoloById(@PathVariable(name = "idFascicolo") Integer idFascicolo){
        return ResponseEntity.ok(gestioneService.getFascicoloById(idFascicolo));
    }

    @GetMapping(value = "getAllFascicoli")
    public ResponseEntity<?> getAllFascicoli(){
        return ResponseEntity.ok(gestioneService.getAllFascicoli());
    }

    @GetMapping(value = "getSegnalazioniByFascicolo/{idFascicolo}")
    public ResponseEntity<?> getSegnalazioniByFascicolo(@PathVariable(name = "idFascicolo") Integer idFascicolo){
        return ResponseEntity.ok(gestioneService.getSegnalazioniByFascicolo(idFascicolo));
    }

    @GetMapping(value = "getFascicoloBySegnalazione/{idSegnalazione}")
    public ResponseEntity<?> getFascicoloBySegnalazione(@PathVariable(name = "idSegnalazione") Integer idInfo){
        return ResponseEntity.ok(gestioneService.getFascicoloBySegnalazioneId(idInfo));
    }

    @GetMapping(value = "getInfoSegnalazioneById/{idInfo}")
    public ResponseEntity<?> getInfoSegnalazioneById(@PathVariable(name = "idInfo") Integer idInfo){
        return ResponseEntity.ok(gestioneService.getInfoById(idInfo));
    }

    @GetMapping("downloadAllegato/{id}")
    public ResponseEntity<byte[]> downloadAllegato(@PathVariable(name = "id") Integer id, HttpServletResponse response) {

        try {
            byte[] contents = gestioneService.downloadAllegato(id);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            //headers.setContentType(MediaType.APPLICATION_XML);
            String filename = "allegato.pdf";
            headers.setContentDispositionFormData(filename, filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return new ResponseEntity<>(contents, headers, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "getAllegatiByInfo/{idInfo}")
    public ResponseEntity<?> getAllegatiByInfo(@PathVariable(name = "idInfo") Integer idInfo){
        return ResponseEntity.ok(gestioneService.getAllegatiByInfo(idInfo));
    }

    @GetMapping(value = "getChat/{idInfo}")
    public ResponseEntity<?> getChat(@PathVariable(name = "idInfo") Integer idInfo){
        return ResponseEntity.ok(gestioneService.getChat(idInfo));
    }

    @PostMapping(value = "addMessage/{idInfo}/{idUtente}")
    public ResponseEntity<?> addMessage(@PathVariable(name = "idInfo") Integer idInfo, @PathVariable(name = "idUtente") Integer idUtente, @RequestBody MessaggioDto messaggioDto){
        try{
            gestioneService.addMessage(idInfo, idUtente, messaggioDto);
            return ResponseEntity.ok().build();
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "addCommento/{idInfo}")
    public ResponseEntity<?> addCommento(@RequestBody Commento c, @PathVariable(name = "idInfo") Integer idInfo){
        try{
            Commento com = gestioneService.addCommento(c, idInfo);
            return ResponseEntity.ok(com);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "addAllegato/{idInfo}")
    public ResponseEntity<?> addAllegato(@RequestBody AllegatoDto a, @PathVariable(name = "idInfo") Integer idInfo){
        try{
            gestioneService.addAllegato(a, idInfo);
            return ResponseEntity.ok().build();
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
