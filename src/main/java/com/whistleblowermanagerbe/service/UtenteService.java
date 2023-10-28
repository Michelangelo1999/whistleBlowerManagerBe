package com.whistleblowermanagerbe.service;

import com.whistleblowermanagerbe.repo.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;
}
