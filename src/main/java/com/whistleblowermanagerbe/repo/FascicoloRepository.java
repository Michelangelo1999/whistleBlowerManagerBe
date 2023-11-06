package com.whistleblowermanagerbe.repo;

import com.whistleblowermanagerbe.model.Fascicolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FascicoloRepository extends JpaRepository<Fascicolo, Integer> {

    @Query(value = "select * from fascicolo where id = (select fk_fascicolo from info_segnalazione where id = :idInfoSegnalazione)", nativeQuery = true)
    Optional<Fascicolo> findByInfoSegnalazione(Integer idInfoSegnalazione);
}
