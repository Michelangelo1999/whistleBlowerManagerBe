package com.whistleblowermanagerbe.repo;

import com.whistleblowermanagerbe.dto.NumeroUtentiDto;
import com.whistleblowermanagerbe.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Integer> {

    Optional<Utente> findByNomeUtente(String nomeUtente);

    @Query(value = "select u.* from utente u join ruolo_utente ru on u.id = ru.fk_utente join ruolo r on r.id = ru.fk_ruolo where r.ruolo = :ruolo", nativeQuery = true)
    List<Utente> findAllByRoles(String ruolo);

    @Query(value = "select count(u.id) from utente u join ruolo_utente ru on u.id = ru.fk_utente join ruolo r on r.id = ru.fk_ruolo where r.ruolo = :ruolo", nativeQuery = true)
    Integer countUser(String ruolo);

    @Query(value = "update utente set abilitato = :abilitato where id = :idUtente", nativeQuery = true)
    @Modifying
    void abilitaUtente(Integer idUtente, Boolean abilitato);

    @Query(value = "update utente set cancellato = true where id = :idUtente", nativeQuery = true)
    @Modifying
    void eliminaUtente(Integer idUtente);

    @Query(value = "select nome from utente where id = (select fk_istruttore from info_segnalazione where fk_segnalazione = :idSegnalazione)", nativeQuery = true)
    String findNomeBySegnalazione(Integer idSegnalazione);

    @Query(value = "select cognome from utente where id = (select fk_istruttore from info_segnalazione where fk_segnalazione = :idSegnalazione)", nativeQuery = true)
    String findCognomeBySegnalazione(Integer idSegnalazione);
}
