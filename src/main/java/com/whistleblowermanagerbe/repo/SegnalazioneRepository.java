package com.whistleblowermanagerbe.repo;

import com.whistleblowermanagerbe.model.Segnalazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SegnalazioneRepository extends JpaRepository<Segnalazione, Integer> {

    @Query(value = "select * from segnalazione where key_16 = :key16", nativeQuery = true)
    Optional<Segnalazione> findByKey16(String key16);
    @Query(value = "select count(id) from segnalazione", nativeQuery = true)
    Integer countSegnalazioni();

}
