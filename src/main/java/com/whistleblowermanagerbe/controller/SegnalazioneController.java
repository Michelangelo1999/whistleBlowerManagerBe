package com.whistleblowermanagerbe.controller;

import com.whistleblowermanagerbe.dto.SegnalazioneDto;
import com.whistleblowermanagerbe.service.SegnalazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "public/segnalazione")
public class SegnalazioneController {

    @Autowired
    private SegnalazioneService service;

    @PostMapping(value = "addSegnalazione")
    public ResponseEntity<?> addSegnalazione(@RequestBody SegnalazioneDto dto){
        try{
            return ResponseEntity.ok(service.addSegnalazione(dto));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "getSegnalazione")
    public ResponseEntity<?> getSegnalazione(@RequestBody String key16){
        try{
            return ResponseEntity.ok(service.getSegnalazioneByKey(key16));
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
