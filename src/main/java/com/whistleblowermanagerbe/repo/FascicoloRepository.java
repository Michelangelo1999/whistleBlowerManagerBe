package com.whistleblowermanagerbe.repo;

import com.whistleblowermanagerbe.model.Fascicolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FascicoloRepository extends JpaRepository<Fascicolo, Integer> {
}
