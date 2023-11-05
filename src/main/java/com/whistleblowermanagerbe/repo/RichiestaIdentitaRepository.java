package com.whistleblowermanagerbe.repo;

import com.whistleblowermanagerbe.model.RichiestaIdentita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RichiestaIdentitaRepository extends JpaRepository<RichiestaIdentita, Integer> {

    @Query(value = "select * from richiesta_identita where fk_segnalazione = :idSegnalazione", nativeQuery = true)
    Optional<RichiestaIdentita> findBySegnalazione(Integer idSegnalazione);
}
