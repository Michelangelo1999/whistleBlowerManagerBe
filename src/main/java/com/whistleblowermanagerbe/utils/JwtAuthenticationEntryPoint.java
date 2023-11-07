package com.whistleblowermanagerbe.utils;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // In questo metodo, puoi gestire le eccezioni relative all'autenticazione JWT.
        // Ad esempio, puoi restituire una risposta JSON personalizzata per le richieste non autorizzate.
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Accesso non autorizzato");
    }
}
