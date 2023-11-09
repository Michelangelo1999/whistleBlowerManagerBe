package com.whistleblowermanagerbe.controller;

import com.whistleblowermanagerbe.dto.LoginRequest;
import com.whistleblowermanagerbe.dto.SegnalazioneDto;
import com.whistleblowermanagerbe.model.Segnalazione;
import com.whistleblowermanagerbe.repo.SegnalazioneRepository;
import com.whistleblowermanagerbe.service.SegnalazioneService;
import com.whistleblowermanagerbe.service.UtenteService;
import com.whistleblowermanagerbe.utils.AuthenticationResponse;
import com.whistleblowermanagerbe.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "public")
public class PublicController {

    @Autowired
    private UtenteService utenteService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private SegnalazioneService segnalazioneService;

    @PostMapping(value = "login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(utenteService.login(loginRequest));
    }

    @PostMapping(value = "auth")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest) {
        /*
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getNomeUtente(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            e.printStackTrace();
        }

         */

        if(authenticationRequest.getNomeUtente().equals("segnalante")){
            try {
                SegnalazioneDto dto = segnalazioneService.getSegnalazioneByKey(authenticationRequest.getKey());

                final UserDetails userDetails = utenteService.loadUserByUsername(authenticationRequest.getNomeUtente());
                final String token = jwtUtil.generateToken(userDetails);

                return ResponseEntity.ok(new AuthenticationResponse(token));
            }catch (Exception e){
                return ResponseEntity.badRequest().build();
            }
        } else {
            final UserDetails userDetails = utenteService.loadUserByUsername(authenticationRequest.getNomeUtente());
            final String token = jwtUtil.generateToken(userDetails);

            return ResponseEntity.ok(new AuthenticationResponse(token));
        }

    }

    @GetMapping(value = "gestisciSegnalazioniGiornalmente")
    public ResponseEntity<?> gestisciSegnalazioniGiornalmente(){
        try{
            segnalazioneService.gestisciSegnalazioniGiornalmente();
            return ResponseEntity.ok().build();
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }


}
