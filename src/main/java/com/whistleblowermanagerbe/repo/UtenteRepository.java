package com.whistleblowermanagerbe.repo;

import com.whistleblowermanagerbe.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Integer> {


}
