package com.whistleblowermanagerbe.controller;

import com.whistleblowermanagerbe.dto.LoginRequest;
import com.whistleblowermanagerbe.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "public")
public class PublicController {

    @Autowired
    private UtenteService utenteService;

    @PostMapping(value = "login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(utenteService.login(loginRequest));
    }
}
