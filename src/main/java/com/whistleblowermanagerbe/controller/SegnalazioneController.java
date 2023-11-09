package com.whistleblowermanagerbe.controller;

import com.whistleblowermanagerbe.dto.SegnalazioneDto;
import com.whistleblowermanagerbe.service.SegnalazioneService;
import com.whistleblowermanagerbe.service.UtenteService;
import com.whistleblowermanagerbe.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "public/segnalazione")
public class SegnalazioneController {

    @Autowired
    private SegnalazioneService service;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private JwtUtil jwtUtil;

    private static final Logger log = LoggerFactory.getLogger(SegnalazioneController.class);

    @PostMapping(value = "addSegnalazione")
    public ResponseEntity<?> addSegnalazione(@RequestBody SegnalazioneDto dto){
        try{
            log.info("in add segnalazione");
            return ResponseEntity.ok(service.addSegnalazione(dto));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "getSegnalazione/{richiediToken}")
    public ResponseEntity<?> getSegnalazione(@RequestBody String key16, @PathVariable(name = "richiediToken") Boolean richiediToken){

        try {
            SegnalazioneDto out = service.getSegnalazioneByKey(key16);
            if(richiediToken){
                final UserDetails userDetails = utenteService.loadUserByKey(key16);
                out.setToken(jwtUtil.generateToken(userDetails));
            }
            return ResponseEntity.ok(out);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
