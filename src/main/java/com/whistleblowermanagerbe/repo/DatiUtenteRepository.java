package com.whistleblowermanagerbe.repo;

import com.whistleblowermanagerbe.model.DatiUtente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DatiUtenteRepository extends JpaRepository<DatiUtente, Integer> {

    Optional<DatiUtente> findByCodiceFiscale(String codiceFiscale);
}
