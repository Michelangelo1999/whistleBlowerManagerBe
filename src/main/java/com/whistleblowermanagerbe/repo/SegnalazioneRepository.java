package com.whistleblowermanagerbe.repo;

import com.whistleblowermanagerbe.model.Segnalazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SegnalazioneRepository extends JpaRepository<Segnalazione, Integer> {

    Optional<Segnalazione> findByKey16(String key16);
}
