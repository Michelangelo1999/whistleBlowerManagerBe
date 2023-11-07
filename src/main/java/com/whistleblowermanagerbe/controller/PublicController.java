package com.whistleblowermanagerbe.controller;

import com.whistleblowermanagerbe.dto.LoginRequest;
import com.whistleblowermanagerbe.service.UtenteService;
import com.whistleblowermanagerbe.utils.AuthenticationResponse;
import com.whistleblowermanagerbe.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "public")
public class PublicController {

    @Autowired
    private UtenteService utenteService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

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

        final UserDetails userDetails = utenteService.loadUserByUsername(authenticationRequest.getNomeUtente());
        final String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }


}
