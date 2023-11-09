package com.whistleblowermanagerbe.repo;

import com.whistleblowermanagerbe.model.InfoSegnalazione;
import com.whistleblowermanagerbe.model.Segnalazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InfoSegnalazioneRepository extends JpaRepository<InfoSegnalazione, Integer> {

    @Query(value = "select * from info_segnalazione where fk_istruttore = :idIstruttore and stato != 'ARCHIVIATA' order by data_creazione asc", nativeQuery = true)
    List<InfoSegnalazione> findAllAssegnate(Integer idIstruttore);

    @Query(value = "select * from info_segnalazione where fk_istruttore is null and stato != 'ARCHIVIATA' and stato != 'INOLTRATA_AD_AUTORITA'", nativeQuery = true)
    List<InfoSegnalazione> findAllNonAssegnate();
    @Query(value = "update info_segnalazione set fk_fascicolo = :idFascicolo where id = :idSegnalazione", nativeQuery = true)
    @Modifying
    void addSegnalazioneInFascicolo(Integer idFascicolo, Integer idSegnalazione);

    @Query(value = "select * from info_segnalazione where fk_fascicolo = :idFascicolo and stato != 'ARCHIVIATA' and stato != 'INOLTRATA_AD_AUTORITA'", nativeQuery = true)
    List<InfoSegnalazione> findAllByFascicolo(Integer idFascicolo);
    @Query(value = "select * from info_segnalazione where fk_segnalazione = (select id from segnalazione where id = :idSegnalazione)", nativeQuery = true)
    InfoSegnalazione findByIdSegnalazione(Integer idSegnalazione);

    @Query(value = "select * from info_segnalazione where fk_istruttore is null and stato = 'ARCHIVIATA'", nativeQuery = true)
    List<InfoSegnalazione> findAllArchiviate();

    @Query(value = "select * from info_segnalazione where fk_istruttore is null and stato = 'INOLTRATA_AD_AUTORITA'", nativeQuery = true)
    List<InfoSegnalazione> findAllInoltrateAdAutorita();

}
