package com.whistleblowermanagerbe.repo;

import com.whistleblowermanagerbe.model.InfoSegnalazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfoSegnalazioneRepository extends JpaRepository<InfoSegnalazione, Integer> {
}
