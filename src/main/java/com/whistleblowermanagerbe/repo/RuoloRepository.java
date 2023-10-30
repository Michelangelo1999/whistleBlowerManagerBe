package com.whistleblowermanagerbe.repo;

import com.whistleblowermanagerbe.model.Ruolo;
import com.whistleblowermanagerbe.model.RuoloUtente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface RuoloRepository extends JpaRepository<Ruolo, Integer> {

    Optional<Ruolo> findByRuolo(String ruolo);
}
