package com.whistleblowermanagerbe.repo;

import com.whistleblowermanagerbe.model.RuoloUtente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuoloUtenteRepository extends JpaRepository<RuoloUtente, Integer> {
}
