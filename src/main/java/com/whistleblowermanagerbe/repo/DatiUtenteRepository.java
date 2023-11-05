package com.whistleblowermanagerbe.repo;

import com.whistleblowermanagerbe.model.DatiUtente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DatiUtenteRepository extends JpaRepository<DatiUtente, Integer> {

    Optional<DatiUtente> findByCodiceFiscale(String codiceFiscale);

    @Query(value = "select * from dati_utente where id = (select fk_dati_utente from segnalazione where id = :idSegnalazione)", nativeQuery = true)
    DatiUtente findBySegnalazione(Integer idSegnalazione);
}
