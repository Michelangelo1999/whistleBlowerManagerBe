package com.whistleblowermanagerbe.repo;

import com.whistleblowermanagerbe.model.Allegato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllegatoRepository extends JpaRepository<Allegato, Integer> {
}
